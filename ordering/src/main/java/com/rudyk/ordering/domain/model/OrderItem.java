package com.rudyk.ordering.domain.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many items to one tab
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tab_id", nullable = false)
    private Tab tab;

    @Column(nullable = false, length = 32)
    private String itemCode;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    protected OrderItem() {} // JPA

    public OrderItem(String itemCode, String displayName, BigDecimal unitPrice, int quantity) {
        this.itemCode = Objects.requireNonNull(itemCode);
        this.displayName = Objects.requireNonNull(displayName);
        this.unitPrice = Objects.requireNonNull(unitPrice);
        this.quantity = quantity;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public String getItemCode() { return itemCode; }
    public String getDisplayName() { return displayName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
    public int setQuantity(int quantity) {
        this.quantity = quantity;
        return quantity;
    }
}