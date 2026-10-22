package com.rudyk.ordering.api.controllers;

import com.rudyk.ordering.api.dto.AddOrderItemRequest;
import com.rudyk.ordering.api.dto.OrderItemResponse;
import com.rudyk.ordering.api.dto.TabResponse;
import com.rudyk.ordering.exceptionhandler.TabNotFoundException;
import com.rudyk.ordering.services.TabService;
import com.rudyk.ordering.domain.model.OrderItem;
import com.rudyk.ordering.domain.model.Tab;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tabs")
public class TabController {

    private final TabService tabService;

    public TabController(TabService tabService) {
        this.tabService = tabService;
    }


    @PostMapping
    public TabResponse openTab(@RequestParam("tableNumber") Integer tableNumber) {
        Tab tab = tabService.openTab(tableNumber);
        return toResponse(tab);
    }



    @PostMapping("/{tabId}/items")
    public TabResponse addItem(@PathVariable Long tabId, @RequestBody AddOrderItemRequest req) {
        Tab tab = tabService.addItem(tabId, req.itemCode, req.displayName, req.unitPrice, req.quantity);
        return toResponse(tab);
    }

    @PostMapping("/{tabId}/confirm")
    public TabResponse confirmTab(@PathVariable Long tabId) {
        Tab tab = tabService.confirm(tabId);
        return toResponse(tab);
    }


    @PostMapping("/{tabId}/close")
    public TabResponse closeTab(@PathVariable Long tabId) {
        Tab tab = tabService.closeTab(tabId);
        return toResponse(tab);
    }


    @GetMapping("/{tabId}")
    public TabResponse getTab(@PathVariable Long tabId) {
        return tabService.getTab(tabId)
                .map(this::toResponse)
                .orElseThrow(() -> new TabNotFoundException(tabId));
    }


    @GetMapping
    public List<TabResponse> getAllTabs() {
        return tabService.getAllTabs().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{tabId}/items")
    public List<OrderItemResponse> getItemsFromTab(
            @PathVariable Long tabId) {

        Tab tab = tabService.getTab(tabId)
                .orElseThrow(() -> new TabNotFoundException(tabId));

        return tab.getOrderItems()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private TabResponse toResponse(Tab tab) {
        TabResponse dto = new TabResponse();
        dto.id = tab.getId();
        dto.tableNumber = tab.getTableNumber();
        dto.status = tab.getStatus().name();
        dto.items = tab.getOrderItems().stream().map(this::toDto).collect(Collectors.toList());
        dto.total = tab.getTotal();
        return dto;
    }

    private OrderItemResponse toDto(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.itemCode = item.getItemCode();
        dto.displayName = item.getDisplayName();
        dto.unitPrice = item.getUnitPrice();
        dto.quantity = item.getQuantity();
        dto.price = item.getPrice();
        return dto;
    }
}