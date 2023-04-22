package com.example.panda3.entity.rental;

public enum PaymentMethod {
    CARD("Karta"),
    BANK_TRANSFER("Przelew bankowy"),
    PAYPAL("PayPal"),
    CASH("Gotówka");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

