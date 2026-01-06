package com.rudyk.kitchenbar.domain.vo;



import com.rudyk.kitchenbar.api.dto.ItemRequest;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object for menu item code (e.g., "IPA-01", "BURGER-02").
 */
@Embeddable
public class MenuItem implements Serializable {

    // Adjust the pattern to your needs; this example allows A–Z, 0–9 and dashes.
    private static final Pattern VALID = Pattern.compile("[A-Z0-9-]{2,32}");


    @Column(name = "menu_item_code", nullable = false, length = 32)
    private String code;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected MenuItem() {}

    public MenuItem(String displayName, String code, int quantity) {
        this.displayName = displayName;
        this.code = code;
        this.quantity = quantity;
    }

    public MenuItem(ItemRequest itemRequest) {
        this.code = itemRequest.itemCode;
        this.displayName = itemRequest.displayName;
        this.quantity = itemRequest.quantity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String itemCode) {
        this.code = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return quantity == menuItem.quantity && Objects.equals(code, menuItem.code) && Objects.equals(displayName, menuItem.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, displayName, quantity);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "displayName='" + displayName + '\'' +
                ", itemCode='" + code + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}