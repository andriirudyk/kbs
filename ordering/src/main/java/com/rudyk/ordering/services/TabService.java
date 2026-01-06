package com.rudyk.ordering.services;

import com.rudyk.ordering.adapter.clients.KitchenbarFeignClient;
import com.rudyk.ordering.api.dto.CreateTicketRequest;
import com.rudyk.ordering.api.dto.ItemRequest;
import com.rudyk.ordering.api.dto.TicketResponse;   // create this DTO in ordering
import com.rudyk.ordering.domain.model.OrderItem;
import com.rudyk.ordering.domain.model.Tab;
import com.rudyk.ordering.domain.model.TabStatus;
import com.rudyk.ordering.repositories.TabRepository;
import feign.FeignException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TabService {

    private final TabRepository tabRepository;

    @Resource
    private KitchenbarFeignClient kitchenbarFeignClient;

    public TabService(TabRepository tabRepository) {
        this.tabRepository = tabRepository;
    }

    public Tab openTab(int tableNumber) {
        Tab tab = Tab.open(tableNumber);
        return tabRepository.save(tab);
    }

    public Tab addItem(Long tabId, String itemCode, String displayName, BigDecimal price, int quantity) {
        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabId));
        if (tab.getStatus() != TabStatus.OPEN) {
            throw new IllegalStateException("Cannot add items to closed tab");
        }
        tab.addItem(new OrderItem(itemCode, displayName, price, quantity));
        return tabRepository.save(tab);
    }

    public Tab closeTab(Long tabId) {
        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabId));
        tab.close();
        return tabRepository.save(tab);
    }

    public Optional<Tab> getTab(Long tabId) {
        return tabRepository.findById(tabId);
    }

    /**
     * Confirm the tab and notify KitchenBar by creating a ticket there.
     * We send a single ticket with all items from the tab.
     */
    public Tab confirm(Long tabId) {
        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new NoSuchElementException("Tab not found: " + tabId));

        if (tab.getStatus() != TabStatus.OPEN) {
            throw new IllegalStateException("Only OPEN tabs can be confirmed");
        }


        CreateTicketRequest request = new CreateTicketRequest();
        request.setOrderId(String.valueOf(tab.getId()));
        List<ItemRequest> items = tab.getOrderItems().stream()
                .filter(oi -> oi.getQuantity() > 0)
                .map(oi -> {
                    ItemRequest ir = new ItemRequest();
                    ir.itemCode = oi.getItemCode();
                    ir.displayName = oi.getDisplayName();
                    ir.quantity = oi.getQuantity();
                    return ir;
                })
                .collect(Collectors.toList());
        request.setItems(items);


        try {
            TicketResponse ticket = kitchenbarFeignClient.create(request);

        } catch (FeignException ex) {

            throw new IllegalStateException("Failed to create ticket in KitchenBar: " + ex.contentUTF8(), ex);
        }


        tab.confirm();
        return tabRepository.save(tab);
    }

    public List<Tab> getAllTabs() {
        return tabRepository.findAll();
    }
}