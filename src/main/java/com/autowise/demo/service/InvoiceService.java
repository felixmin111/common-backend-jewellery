package com.autowise.demo.service;

import com.autowise.demo.Util.InvoiceNoGenerator;
import com.autowise.demo.dto.InvoiceResponseDto;
import com.autowise.demo.dto.PurchaseSaveRequestDto;
import com.autowise.demo.mapper.InvoiceMapper;
import com.autowise.demo.model.Invoice;
import com.autowise.demo.model.Product;
import com.autowise.demo.model.PurchaseItem;
import com.autowise.demo.repository.InvoiceRepository;
import com.autowise.demo.repository.ProductRepository;
import com.autowise.demo.repository.PurchaseItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final PurchaseItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final InvoiceNoGenerator invoiceNoGenerator;

    public InvoiceService(InvoiceRepository invoiceRepo,
                          PurchaseItemRepository itemRepo,
                          ProductRepository productRepo,
                          InvoiceNoGenerator invoiceNoGenerator) {
        this.invoiceRepo = invoiceRepo;
        this.itemRepo = itemRepo;
        this.productRepo = productRepo;
        this.invoiceNoGenerator = invoiceNoGenerator;
    }

    @Transactional
    public InvoiceResponseDto create(PurchaseSaveRequestDto req) {

        if (req.getCustomerId() == null)
            throw new IllegalArgumentException("customerId is required");

        if (req.getItems() == null || req.getItems().isEmpty())
            throw new IllegalArgumentException("items is required");

        // ✅ 1) subtotal
        BigDecimal subTotal = BigDecimal.ZERO;

        for (var it : req.getItems()) {
            if (it.getProductId() == null)
                throw new IllegalArgumentException("productId is required");

            if (it.getQty() == null || it.getQty() <= 0)
                throw new IllegalArgumentException("qty must be > 0");

            if (it.getSellingPrice() == null)
                throw new IllegalArgumentException("sellingPrice is required");

            subTotal = subTotal.add(it.getSellingPrice().multiply(BigDecimal.valueOf(it.getQty())));
        }

        // ✅ 2) discount logic (supports amount OR percentage)
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (req.getDiscountAmount() != null) {
            discountAmount = req.getDiscountAmount();
        } else if (req.getDiscountPercentage() != null) {
            BigDecimal pct = req.getDiscountPercentage();
            discountAmount = subTotal.multiply(pct).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        }

        if (discountAmount.compareTo(BigDecimal.ZERO) < 0) discountAmount = BigDecimal.ZERO;
        if (discountAmount.compareTo(subTotal) > 0) discountAmount = subTotal;

        BigDecimal finalPrice = subTotal.subtract(discountAmount);
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) finalPrice = BigDecimal.ZERO;

        // ✅ 3) save invoice first
        Invoice inv = new Invoice();
        inv.setInvoiceNo(invoiceNoGenerator.nextInvoiceNo());
        inv.setCustomerId(req.getCustomerId());
        inv.setSubTotal(subTotal);
        inv.setDiscountAmount(discountAmount);
        inv.setDiscountPercentage(req.getDiscountPercentage());
        inv.setFinalPrice(finalPrice);
        inv.setStatus(normalizeStatus(req.getStatus()));

        Invoice saved = invoiceRepo.save(inv);

        // ✅ 4) subtract product qty
        for (var it : req.getItems()) {
            Product product = productRepo.findById(it.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + it.getProductId()));

            long currentQty = product.getQty() == null ? 0 : product.getQty();
            long buyQty = it.getQty();

            if (buyQty > currentQty) {
                throw new IllegalArgumentException(
                        "Not enough stock for productId=" + it.getProductId() +
                                " (available=" + currentQty + ", requested=" + buyQty + ")"
                );
            }

            long newQty = currentQty - buyQty;

            if (newQty <= 0) {
                product.setQty(0L);
                product.setStockStatus("OUT_OF_STOCK");
            } else {
                product.setQty(newQty);
                // optional: keep your old status if you want
                product.setStockStatus("IN_STOCK");
            }

            productRepo.save(product);
        }

        // ✅ 5) save purchase items
        for (var it : req.getItems()) {
            PurchaseItem pi = new PurchaseItem();
            pi.setInvoice(saved);
            pi.setProductId(it.getProductId());
            pi.setQty(it.getQty());
            pi.setSellingPrice(it.getSellingPrice());

            BigDecimal lineSubtotal = it.getSellingPrice().multiply(BigDecimal.valueOf(it.getQty()));
            pi.setSubtotal(lineSubtotal);
            pi.setDiscountAmount(BigDecimal.ZERO);
            pi.setFinalPrice(lineSubtotal);

            itemRepo.save(pi);
        }

        return getById(saved.getId());
    }

    public InvoiceResponseDto getById(Long id) {
        Invoice inv = invoiceRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));

        List<PurchaseItem> items = itemRepo.findByInvoice_Id(id);
        return InvoiceMapper.toDto(inv, items);
    }

    public List<InvoiceResponseDto> list() {
        return invoiceRepo.findAll().stream()
                .map(inv -> InvoiceMapper.toDto(inv, itemRepo.findByInvoice_Id(inv.getId())))
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        invoiceRepo.deleteById(id);
    }

    private String normalizeStatus(String s) {
        if (s == null) return "DRAFT";
        String x = s.trim().toUpperCase();
        if (x.equals("DRAFT")) return "DRAFT";
        if (x.equals("CONFIRMED") || x.equals("CONFIRM")) return "CONFIRMED";
        return "DRAFT";
    }
}