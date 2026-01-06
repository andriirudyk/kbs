package com.rudyk.catalog.domain.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MenuItemId implements Serializable {

    @Column(name = "menu_id", nullable = false)
    private UUID menuId;

    @Column(name = "item_code", length = 16, nullable = false)
    private String itemCode;

    protected MenuItemId() {
    }

    public MenuItemId(UUID menuId, String itemCode) {
        this.menuId = menuId;
        this.itemCode = itemCode;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public String getItemCode() {
        return itemCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItemId)) return false;
        MenuItemId that = (MenuItemId) o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(itemCode, that.itemCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, itemCode);
    }
}
