package com.rudyk.ordering.services;



import com.rudyk.ordering.domain.model.OrderItem;
import com.rudyk.ordering.domain.model.Tab;
import com.rudyk.ordering.domain.model.TabStatus;
import com.rudyk.ordering.repositories.TabRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;


public class OrderItemService {

    private final TabRepository tabRepository;

    public OrderItemService(TabRepository tabRepository) {
        this.tabRepository = tabRepository;
    }

    // Додаємо позицію (OrderItem) до відкритого табу
    public Tab addOrderItem(Long tabId, String itemCode, String displayName, BigDecimal price, int quantity) {
        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabId));

        if (tab.getStatus() != TabStatus.OPEN) {
            throw new IllegalStateException("Cannot add items to a closed tab");
        }

        OrderItem item = new OrderItem(itemCode, displayName, price, quantity);
        tab.addItem(item);
        tabRepository.save(tab);

        return tab;
    }

    // (Опційно) Видалити позицію з табу
    public Tab removeOrderItem(Long tabId, String itemCode) {
        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabId));

        boolean removed = tab.removeItem(itemCode);
        if (!removed) {
            throw new NoSuchElementException("Item not found in tab: " + itemCode);
        }
        tabRepository.save(tab);

        return tab;
    }

    // (Опційно) Оновити кількість певної позиції
    public Tab updateOrderItemQuantity(Long tabId, String itemCode, int newQuantity) {
        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabId));

        boolean updated = tab.updateItemQuantity(itemCode, newQuantity);
        if (!updated) {
            throw new NoSuchElementException("Item not found in tab: " + itemCode);
        }
        tabRepository.save(tab);

        return tab;
    }
}