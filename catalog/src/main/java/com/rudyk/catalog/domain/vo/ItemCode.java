package com.rudyk.catalog.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class ItemCode {
    @Column(nullable = false, length = 16, unique = false)
    private String code;
    private static final String REGEX = "[A-Z]{2,5}-\\d{2}";

    protected ItemCode() { }
    public ItemCode(String code) {
        if(code == null || !code.matches("[A-Z]{2,5}-\\d{2}"))
            throw new IllegalArgumentException("Invalid item code");
        this.code = code;
    }
    public String getCode() { return code; }
    public static ItemCode of(String raw) { return new ItemCode(raw); }

}