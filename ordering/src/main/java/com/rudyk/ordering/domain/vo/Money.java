package com.rudyk.ordering.domain.vo;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public final class Money implements Serializable, Comparable<Money> {
    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || currency == null)
            throw new IllegalArgumentException("Amount and currency cannot be null");
        if (amount.scale() > currency.getDefaultFractionDigits())
            throw new IllegalArgumentException("Scale exceeds currency precision");
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(double value, String currencyCode) {
        Currency cur = Currency.getInstance(currencyCode);
        return new Money(BigDecimal.valueOf(value).setScale(cur.getDefaultFractionDigits()), cur);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money plus(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }

    public Money minus(Money other) {
        requireSameCurrency(other);
        BigDecimal result = this.amount.subtract(other.amount);
        if (result.signum() < 0) throw new IllegalArgumentException("Negative result");
        return new Money(result, currency);
    }

    private void requireSameCurrency(Money other) {
        if (!currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o instanceof Money
                        && amount.compareTo(((Money) o).amount) == 0
                        && currency.equals(((Money) o).currency));
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros(), currency);
    }

    @Override
    public int compareTo(Money o) {
        requireSameCurrency(o);
        return amount.compareTo(o.amount);
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}