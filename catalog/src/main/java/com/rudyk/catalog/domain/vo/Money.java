package com.rudyk.catalog.domain.vo;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
@Embeddable
public final class Money implements Comparable<Money> {
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false, length = 3)
    private String currency;

    protected Money() { }
    public Money(BigDecimal amount, String currency) {
        if(amount == null || currency == null) throw new IllegalArgumentException();
        this.amount = amount;
        this.currency = currency.toUpperCase();
    }
    public static Money of(double val, String cur) {
        Currency c = Currency.getInstance(cur);
        return new Money(BigDecimal.valueOf(val).setScale(c.getDefaultFractionDigits())
                ,c.getCurrencyCode());
    }

    public Money plus(Money other) { checkSame(other); return new Money(amount.add(other.amount), currency); }
    public Money minus(Money other) { checkSame(other); var r = amount.subtract(other.amount);
        if (r.signum() < 0) throw new IllegalArgumentException("Result < 0"); return new Money(r, currency); }

    private void checkSame(Money o){ if(!currency.equals(o.currency))throw new IllegalArgumentException("Currency mismatch"); }


    @Override public int compareTo(Money o){ checkSame(o); return amount.compareTo(o.amount);}
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }

}