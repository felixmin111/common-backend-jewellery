package com.autowise.demo.Util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceNoGenerator {
    private final JdbcTemplate jdbc;

    public InvoiceNoGenerator(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String nextInvoiceNo() {
        Long seq = jdbc.queryForObject("SELECT nextval('invoice_no_seq')", Long.class);
        return "INV-2026-" + String.format("%05d", seq);
    }
}