package com.rudyk.catalog.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
@Embeddable
public class VatRate {
    @Column(nullable = false)
    private int percent;
    protected VatRate() { }
    public VatRate(int percent) {
        if(percent < 0 || percent > 30)
            throw new IllegalArgumentException("Invalid VAT");
        this.percent = percent;
    }
    public int getPercent() { return percent; }
    public BigDecimal factor() { return BigDecimal.valueOf(1 + percent / 100.0); }
}
