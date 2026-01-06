package com.rudyk.inventoryservice.domain.model;



import com.rudyk.inventoryservice.domain.vo.ItemCode;
import com.rudyk.inventoryservice.domain.vo.Quantity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ItemCode itemCode;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private Quantity quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StockStatus status;

    protected InventoryItem() {} // JPA

    public InventoryItem(ItemCode itemCode, Quantity quantity) {
        this.itemCode = Objects.requireNonNull(itemCode);
        this.quantity = Objects.requireNonNull(quantity);
        this.status = calculateStatus();
    }

    public Long getId() { return id; }
    public ItemCode getItemCode() { return itemCode; }
    public Quantity getQuantity() { return quantity; }
    public StockStatus getStatus() { return status; }

    public void decrease(Quantity amount) {
        if (quantity.isLessThan(amount)) {
            throw new IllegalArgumentException("Insufficient stock for " + itemCode);
        }
        this.quantity = quantity.minus(amount);
        this.status = calculateStatus();
    }

    public void increase(Quantity amount) {
        this.quantity = quantity.plus(amount);
        this.status = calculateStatus();
    }

    private StockStatus calculateStatus() {
        // Наприклад: LOW, якщо менше 5 одиниць, інакше OK
        return quantity.getValue() < 5 ? StockStatus.LOW : StockStatus.OK;
    }
}