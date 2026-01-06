package com.rudyk.catalog.domain.model;


import com.rudyk.catalog.domain.vo.Money;

import javax.persistence.*;

@Entity
public class ModifierOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Embedded
    private Money deltaPrice;




    protected ModifierOption() { }
    public ModifierOption(String name, Money deltaPrice) {
        this.name = name;
        this.deltaPrice = deltaPrice;
    }

    public Money getDeltaPrice() {
        return deltaPrice;
    }

    public void setDeltaPrice(Money deltaPrice) {
        this.deltaPrice = deltaPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
