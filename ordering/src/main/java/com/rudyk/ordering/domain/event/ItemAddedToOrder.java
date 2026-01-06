package com.rudyk.ordering.domain.event;


import com.rudyk.ordering.domain.vo.ItemCode;
import com.rudyk.ordering.domain.vo.TabId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public final class ItemAddedToOrder implements Serializable {
    private final TabId tabId;
    private final ItemCode itemCode;
    private final String displayName;
    private final int quantity;
    private final BigDecimal unitPrice;

    public ItemAddedToOrder(TabId tabId, ItemCode itemCode, String displayName, int quantity, BigDecimal unitPrice) {
        this.tabId = Objects.requireNonNull(tabId);
        this.itemCode = Objects.requireNonNull(itemCode);
        this.displayName = Objects.requireNonNull(displayName);
        this.quantity = quantity;
        this.unitPrice = Objects.requireNonNull(unitPrice);
    }

    public TabId getTabId() { return tabId; }
    public ItemCode getItemCode() { return itemCode; }
    public String getDisplayName() { return displayName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemAddedToOrder)) return false;
        ItemAddedToOrder that = (ItemAddedToOrder) o;
        return quantity == that.quantity &&
                tabId.equals(that.tabId) &&
                itemCode.equals(that.itemCode) &&
                displayName.equals(that.displayName) &&
                unitPrice.compareTo(that.unitPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tabId, itemCode, displayName, quantity, unitPrice.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return "ItemAddedToOrder{" +
                "tabId=" + tabId +
                ", itemCode=" + itemCode +
                ", displayName='" + displayName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}