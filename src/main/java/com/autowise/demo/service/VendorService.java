package com.autowise.demo.service;

import com.autowise.demo.dto.VendorDto;
import com.autowise.demo.dto.VendorInvoiceItemDto;
import com.autowise.demo.dto.VendorInvoiceLookupDto;
import com.autowise.demo.mapper.VendorMapper;
import com.autowise.demo.model.*;
import com.autowise.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    private final InvoiceRepository invoiceRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final VendorItemRepository vendorItemRepository;
    private VendorDto toDto(Vendor vendor) {
        String invoiceNo = null;

        if (vendor.getInvoiceId() != null) {
            invoiceNo = invoiceRepository.findById(vendor.getInvoiceId())
                    .map(Invoice::getInvoiceNo)
                    .orElse(null);
        }

        return VendorDto.builder()
                .id(vendor.getId())
                .invoiceId(vendor.getInvoiceId())
                .invoiceNo(invoiceNo)
                .customerId(vendor.getCustomerId())
                .desc(vendor.getDesc())
                .buybackDate(vendor.getBuybackDate())
                .totalBuybackPrice(vendor.getTotalBuybackPrice())
                .items(null)
                .build();
    }

    public List<VendorDto> getAll() {
        return vendorRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public VendorDto getById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + id));
        return toDto(vendor);
    }
    public VendorDto create(VendorDto request) {
        if (request.getInvoiceId() == null) {
            throw new RuntimeException("invoiceId is required");
        }

        if (request.getCustomerId() == null) {
            throw new RuntimeException("customerId is required");
        }

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Sell back items are required");
        }

        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found: " + request.getInvoiceId()));

        if (!invoice.getCustomerId().equals(request.getCustomerId())) {
            throw new RuntimeException("Customer does not match the selected invoice");
        }

        // 1) calculate total
        BigDecimal totalBuybackPrice = BigDecimal.ZERO;

        for (var item : request.getItems()) {
            if (item.getPurchaseItemId() == null) {
                throw new RuntimeException("purchaseItemId is required");
            }

            if (item.getProductId() == null) {
                throw new RuntimeException("productId is required");
            }

            if (item.getQty() == null || item.getQty() <= 0) {
                throw new RuntimeException("qty must be greater than 0");
            }

            BigDecimal finalPrice = item.getFinalBuybackPrice() == null
                    ? BigDecimal.ZERO
                    : item.getFinalBuybackPrice();

            if (finalPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("finalBuybackPrice cannot be negative");
            }

            totalBuybackPrice = totalBuybackPrice.add(finalPrice);
        }

        // 2) save vendor header
        Vendor vendor = new Vendor();
        vendor.setInvoiceId(request.getInvoiceId());
        vendor.setCustomerId(request.getCustomerId());
        vendor.setDesc(request.getDesc());
        vendor.setBuybackDate(request.getBuybackDate());
        vendor.setTotalBuybackPrice(totalBuybackPrice);

        Vendor savedVendor = vendorRepository.save(vendor);

        // 3) save vendor items
        for (var item : request.getItems()) {
            PurchaseItem purchaseItem = purchaseItemRepository.findById(item.getPurchaseItemId())
                    .orElseThrow(() -> new RuntimeException("Purchase item not found: " + item.getPurchaseItemId()));

            if (!purchaseItem.getInvoice().getId().equals(request.getInvoiceId())) {
                throw new RuntimeException("Purchase item does not belong to selected invoice");
            }

            if (!purchaseItem.getProductId().equals(item.getProductId())) {
                throw new RuntimeException("Product does not match purchase item");
            }

            VendorItem vendorItem = new VendorItem();
            vendorItem.setVendorId(savedVendor.getId());
            vendorItem.setPurchaseItemId(item.getPurchaseItemId());
            vendorItem.setProductId(item.getProductId());
            vendorItem.setQty(item.getQty());
            vendorItem.setSellingPrice(item.getSellingPrice());
            vendorItem.setDeductionAmount(item.getDeductionAmount() == null ? BigDecimal.ZERO : item.getDeductionAmount());
            vendorItem.setFinalBuybackPrice(item.getFinalBuybackPrice());

            vendorItemRepository.save(vendorItem);

            // 4) optional: return stock back to product
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            long currentQty = product.getQty() == null ? 0L : product.getQty();
            long returnQty = item.getQty();

            long newQty = currentQty + returnQty;
            product.setQty(newQty);

            if (newQty > 0) {
                product.setStockStatus("IN_STOCK");
            }

            productRepository.save(product);
        }

        // 5) return dto
        return toDto(savedVendor);
    }

    public VendorDto update(Long id, VendorDto request) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found: " + id));

        vendor.setInvoiceId(request.getInvoiceId());
        vendor.setCustomerId(request.getCustomerId());
        vendor.setDesc(request.getDesc());
        vendor.setBuybackDate(request.getBuybackDate());
        vendor.setTotalBuybackPrice(request.getTotalBuybackPrice());

        Vendor saved = vendorRepository.save(vendor);
        return toDto(saved);
    }

    public void delete(Long id) {
        if (!vendorRepository.existsById(id)) {
            throw new RuntimeException("Vendor not found: " + id);
        }
        vendorRepository.deleteById(id);
    }
    public VendorInvoiceLookupDto findInvoiceForBuyback(String invoiceNo) {
        if (invoiceNo == null || invoiceNo.trim().isEmpty()) {
            throw new IllegalArgumentException("invoiceNo is required");
        }

        Invoice invoice = invoiceRepository.findByInvoiceNo(invoiceNo.trim())
                .orElseThrow(() -> new RuntimeException("Invoice not found: " + invoiceNo));

        List<PurchaseItem> purchaseItems = purchaseItemRepository.findByInvoice_Id(invoice.getId());

        Customer customer = customerRepository.findById(invoice.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + invoice.getCustomerId()));

        List<VendorInvoiceItemDto> items = purchaseItems.stream()
                .map(pi -> {
                    Product product = productRepository.findById(pi.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + pi.getProductId()));

                    return VendorInvoiceItemDto.builder()
                            .purchaseItemId(pi.getId())
                            .productId(pi.getProductId())
                            .productName(product.getName())
                            .qty(pi.getQty())
                            .sellingPrice(pi.getSellingPrice())
                            .finalPrice(pi.getFinalPrice())
                            .build();
                })
                .toList();

        return VendorInvoiceLookupDto.builder()
                .invoiceId(invoice.getId())
                .invoiceNo(invoice.getInvoiceNo())
                .customerId(invoice.getCustomerId())
                .customerName(customer.getName())
                .customerPhone(customer.getPhone())
                .items(items)
                .build();
    }
}