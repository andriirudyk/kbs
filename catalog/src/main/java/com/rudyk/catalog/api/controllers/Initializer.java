package com.rudyk.catalog.api.controllers;

import com.rudyk.catalog.domain.model.Category;
import com.rudyk.catalog.domain.model.Menu;
import com.rudyk.catalog.domain.model.MenuItem;
import com.rudyk.catalog.domain.model.MenuStatus;
import com.rudyk.catalog.repositories.CategoryRepository;
import com.rudyk.catalog.repositories.MenuRepository;
import com.rudyk.catalog.domain.vo.ItemCode;
import com.rudyk.catalog.domain.vo.Money;
import com.rudyk.catalog.domain.vo.VatRate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Component
public class Initializer implements CommandLineRunner {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    public Initializer(MenuRepository menuRepository, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
// 1. Create or get Categories
        Category drinks = categoryRepository.findById("DRINKS")
                .orElseGet(() -> categoryRepository.save(new Category("DRINKS", "Drinks")));
        Category mains = categoryRepository.findById("MAINS")
                .orElseGet(() -> categoryRepository.save(new Category("MAINS", "Main Dishes")));

        // 2. Create a Menu with items
        Menu menu = new Menu(
                LocalDateTime.now().minusHours(1),   // valid from 1 hour ago
                LocalDateTime.now().plusDays(7));    // valid for next 7 days

        // IPA
        MenuItem ipa = new MenuItem(
                new ItemCode("IPA-01"),
                "IPA Beer",
                "Craft India Pale Ale",
                drinks,
                new Money(BigDecimal.valueOf(4.5), "EUR"),
                null,
                new VatRate(10)
        );
        menu.addItem(ipa);

        // Burger
        MenuItem burger = new MenuItem(
                new ItemCode("BURG-01"),
                "Classic Burger",
                "Beef patty, cheddar, lettuce, tomato, bun",
                mains,
                new Money(BigDecimal.valueOf(8.0), "EUR"),
                new Money(BigDecimal.valueOf(7.0), "EUR"),
                new VatRate(10)
        );
        menu.addItem(burger);

        // Fries
        MenuItem fries = new MenuItem(
                new ItemCode("FRIES-01"),
                "French Fries",
                "Crispy golden fries",
                mains,
                new Money(BigDecimal.valueOf(3.0), "EUR"),
                null,
        new VatRate(10)
        );
        menu.addItem(fries);

        menu.setStatus(MenuStatus.PUBLISHED);

        menuRepository.save(menu);

        System.out.println("Sample menu initialized with 3 items.");
    }
}
