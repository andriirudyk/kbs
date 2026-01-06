package com.rudyk.kitchenbar.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String description;

    public Long getId() { return id; }
    public String getName() { return name; }
    public Long getPrice() { return price; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setPrice(Long price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
}