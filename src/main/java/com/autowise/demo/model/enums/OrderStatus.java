package com.autowise.demo.model.enums;

public enum OrderStatus {
    PENDING,        // created but not paid/confirmed yet
    CONFIRMED,      // confirmed, stock/material reserved
    BACKORDERED,    // waiting stock
    IN_PROGRESS,    // custom making
    COMPLETED,
    CANCELLED
}