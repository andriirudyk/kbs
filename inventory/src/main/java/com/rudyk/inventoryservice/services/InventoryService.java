package com.rudyk.inventoryservice.services;




import com.rudyk.inventoryservice.domain.event.StockLow;
import com.rudyk.inventoryservice.domain.model.InventoryItem;
import com.rudyk.inventoryservice.domain.model.StockStatus;
import com.rudyk.inventoryservice.repositories.InventoryRepository;
import com.rudyk.inventoryservice.domain.vo.ItemCode;
import com.rudyk.inventoryservice.domain.vo.Quantity;
import com.rudyk.inventoryservice.domain.port.out.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final EventPublisher eventPublisher;

    public InventoryService(InventoryRepository inventoryRepository,
                            EventPublisher eventPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Зменшити залишок товару (після оплати, списання)
     */
    @Transactional
    public InventoryItem decreaseStock(ItemCode itemCode, Quantity amount) {
        InventoryItem item = inventoryRepository.findByItemCode(itemCode)
                .orElseThrow(() -> new NoSuchElementException("Item not found: " + itemCode));

        item.decrease(amount);

        inventoryRepository.save(item);

        if (item.getStatus() == StockStatus.LOW) {
            eventPublisher.publish(new StockLow(item.getItemCode(), item.getQuantity()));
        }

        return item;
    }

    /**
     * Отримати стан запасу за кодом
     */
    @Transactional(readOnly = true)
    public Optional<InventoryItem> getInventoryItem(ItemCode itemCode) {
        return inventoryRepository.findByItemCode(itemCode);
    }

    /**
     * Додати новий товар до складу
     */
    @Transactional
    public InventoryItem addInventoryItem(ItemCode itemCode, Quantity initial) {
        InventoryItem item = new InventoryItem(itemCode, initial);
        return inventoryRepository.save(item);
    }
}