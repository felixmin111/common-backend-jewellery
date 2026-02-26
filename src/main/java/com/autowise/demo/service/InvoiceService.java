package com.autowise.demo.service;

import com.autowise.demo.Util.InvoiceNoGenerator;
import com.autowise.demo.dto.InvoiceResponseDto;
import com.autowise.demo.dto.PurchaseSaveRequestDto;
import com.autowise.demo.mapper.InvoiceMapper;
import com.autowise.demo.model.Invoice;
import com.autowise.demo.model.PurchaseItem;
import com.autowise.demo.repository.InvoiceRepository;
import com.autowise.demo.repository.PurchaseItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final PurchaseItemRepository itemRepo;
    private final InvoiceNoGenerator invoiceNoGenerator;

    public InvoiceService(InvoiceRepository invoiceRepo,
                          PurchaseItemRepository itemRepo,
                          InvoiceNoGenerator invoiceNoGenerator) {
        this.invoiceRepo = invoiceRepo;
        this.itemRepo = itemRepo;
        this.invoiceNoGenerator = invoiceNoGenerator;
    }

    @Transactional
    public InvoiceResponseDto create(PurchaseSaveRequestDto req) {
        if (req.getCustomerId() == null) throw new IllegalArgumentException("customerId is required");
        if (req.getItems() == null || req.getItems().isEmpty()) throw new IllegalArgumentException("items is required");

        // subtotal
        BigDecimal subTotal = BigDecimal.ZERO;
        for (var it : req.getItems()) {
            if (it.getProductId() == null) throw new IllegalArgumentException("productId is required");
            if (it.getQty() == null || it.getQty() <= 0) throw new IllegalArgumentException("qty must be > 0");
            if (it.getSellingPrice() == null) throw new IllegalArgumentException("sellingPrice is required");
            subTotal = subTotal.add(it.getSellingPrice().multiply(BigDecimal.valueOf(it.getQty())));
        }

        BigDecimal discountAmount = req.getDiscountAmount() != null ? req.getDiscountAmount() : BigDecimal.ZERO;
        BigDecimal finalPrice = subTotal.subtract(discountAmount);
        if (finalPrice.compareTo(BigDecimal.ZERO) < 0) finalPrice = BigDecimal.ZERO;

        // 1) save invoice first
        Invoice inv = new Invoice();
        inv.setInvoiceNo(invoiceNoGenerator.nextInvoiceNo());
        inv.setCustomerId(req.getCustomerId());
        inv.setSubTotal(subTotal);
        inv.setDiscountAmount(discountAmount);
        inv.setDiscountPercentage(req.getDiscountPercentage());
        inv.setFinalPrice(finalPrice);
        inv.setStatus(normalizeStatus(req.getStatus()));

        Invoice saved = invoiceRepo.save(inv);

        // 2) save purchase items (invoice_id)
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
        Invoice inv = invoiceRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invoice not found: " + id));
        List<PurchaseItem> items = itemRepo.findByInvoice_Id(id);
        return InvoiceMapper.toDto(inv, items);
    }

    public List<InvoiceResponseDto> list() {
        return invoiceRepo.findAll().stream()
                .map(inv -> toDto(inv, itemRepo.findByInvoice_Id(inv.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        invoiceRepo.deleteById(id); // purchase auto delete because ON DELETE CASCADE
    }

    private String normalizeStatus(String s) {
        if (s == null) return "DRAFT";
        String x = s.trim().toUpperCase();
        if (x.equals("DRAFT")) return "DRAFT";
        if (x.equals("CONFIRMED") || x.equals("CONFIRM")) return "CONFIRMED";
        return "DRAFT";
    }

    private InvoiceResponseDto toDto(Invoice inv, List<PurchaseItem> items) {
        InvoiceResponseDto dto = new InvoiceResponseDto();
        dto.setId(inv.getId());
        dto.setInvoiceNo(inv.getInvoiceNo());
        dto.setCustomerId(inv.getCustomerId());
        dto.setSubTotal(inv.getSubTotal());
        dto.setDiscountAmount(inv.getDiscountAmount());
        dto.setDiscountPercentage(inv.getDiscountPercentage());
        dto.setFinalPrice(inv.getFinalPrice());
        dto.setStatus(inv.getStatus());
        dto.setCreatedAt(inv.getCreatedAt());
        dto.setUpdatedAt(inv.getUpdatedAt());

        var itemDtos = items.stream().map(it -> {
            InvoiceResponseDto.ItemDto i = new InvoiceResponseDto.ItemDto();
            i.setId(it.getId());
            i.setProductId(it.getProductId());
            i.setQty(it.getQty());
            i.setSellingPrice(it.getSellingPrice());
            i.setSubtotal(it.getSubtotal());
            i.setDiscountAmount(it.getDiscountAmount());
            i.setFinalPrice(it.getFinalPrice());
            return i;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}