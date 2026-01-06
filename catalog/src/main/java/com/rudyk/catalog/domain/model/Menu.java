package com.rudyk.catalog.domain.model;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime validFrom;

    @Column(nullable = false)
    private LocalDateTime validTo;

    @Enumerated(EnumType.STRING)
    private MenuStatus status = MenuStatus.DRAFT;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> items = new ArrayList<>();

    protected Menu() { }
    public Menu(LocalDateTime from, LocalDateTime to) {
        this.validFrom = from;
        this.validTo = to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public MenuStatus getStatus() {
        return status;
    }

    public void setStatus(MenuStatus status) {
        this.status = status;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public void addItem(MenuItem item) {
        if(item == null) throw new IllegalArgumentException("Item cannot be null");
        if(item.getMenu() != null) throw new IllegalArgumentException("Item already belongs to another menu");
        item.setMenu(this);
        items.add(item);
    }
}
