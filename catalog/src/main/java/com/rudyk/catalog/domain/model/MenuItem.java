package com.rudyk.catalog.domain.model;


import com.rudyk.catalog.domain.vo.ItemCode;
import com.rudyk.catalog.domain.vo.Money;
import com.rudyk.catalog.domain.vo.VatRate;

import javax.persistence.*;
import java.util.*;

@Entity
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Menu menu;

    @Embedded
    private ItemCode itemCode;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = true)
    private String description;

    @ManyToOne(optional = false)
    private Category category;

    @Embedded
    private Money basePrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "promo_price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "promo_price_currency"))
    })
    private Money promoPrice;

    @Embedded
    private VatRate vatRate;

    @Column(nullable = false)
    private boolean available = true;

    @ManyToMany
    @JoinTable(
            name = "menuitem_modifiergroup",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<ModifierGroup> modifierGroups = new ArrayList<>();

    protected MenuItem() {
    }

    public MenuItem(ItemCode itemCode, String displayName, String description,
                    Category category, Money basePrice, Money promoPrice, VatRate vatRate) {
        this.basePrice = basePrice;
        this.category = category;
        this.description = description;
        this.displayName = displayName;
        this.itemCode = itemCode;
        this.promoPrice = promoPrice;
        this.vatRate = vatRate;
    }

    public MenuItem(boolean available, Money basePrice, Category category,
                    String description, String displayName, Long id, ItemCode itemCode, Menu menu, List<ModifierGroup> modifierGroups, Money promoPrice, VatRate vatRate) {
        this.available = available;
        this.basePrice = basePrice;
        this.category = category;
        this.description = description;
        this.displayName = displayName;
        this.id = id;
        this.itemCode = itemCode;
        this.menu = menu;
        this.modifierGroups = modifierGroups;
        this.promoPrice = promoPrice;
        this.vatRate = vatRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Money basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemCode getItemCode() {
        return itemCode;
    }

    public void setItemCode(ItemCode itemCode) {
        this.itemCode = itemCode;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<ModifierGroup> getModifierGroups() {
        return modifierGroups;
    }

    public void setModifierGroups(List<ModifierGroup> modifierGroups) {
        this.modifierGroups = modifierGroups;
    }

    public Money getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Money promoPrice) {
        this.promoPrice = promoPrice;
    }

    public VatRate getVatRate() {
        return vatRate;
    }

    public void setVatRate(VatRate vatRate) {
        this.vatRate = vatRate;
    }
}
