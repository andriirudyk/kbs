package com.rudyk.inventoryservice.api.controllers;



import com.rudyk.inventoryservice.services.InventoryService;
import com.rudyk.inventoryservice.domain.model.InventoryItem;
import com.rudyk.inventoryservice.domain.vo.ItemCode;
import com.rudyk.inventoryservice.domain.vo.Quantity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
//@FIXME change the endpoints cause itemCode doesn't work
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @GetMapping("/item")
    public InventoryItemResponse getByItemCode(@RequestParam("itemCode") String itemCode) {
        Optional<InventoryItem> item = inventoryService.getInventoryItem(new ItemCode(itemCode));
        return item.map(InventoryItemResponse::fromDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemCode));
    }
    // Перевірити залишок по коду
    @GetMapping("/items/{itemCode}")
    public InventoryItemResponse getStock(@PathVariable String itemCode) {
        Optional<InventoryItem> item = inventoryService.getInventoryItem(new ItemCode(itemCode));
        return item.map(InventoryItemResponse::fromDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemCode));
    }

    // Списати товар (наприклад, після продажу)
    @PatchMapping("/stock")
    public InventoryItemResponse decreaseStock(@RequestBody DecreaseStockRequest request) {
        InventoryItem item = inventoryService.decreaseStock(
                new ItemCode(request.itemCode),
                new Quantity(request.amount)
        );
        return InventoryItemResponse.fromDomain(item);
    }

    // DTO для відповіді
    public static class InventoryItemResponse {
        public String itemCode;
        public int quantity;
        public String status;

        public static InventoryItemResponse fromDomain(InventoryItem item) {
            InventoryItemResponse dto = new InventoryItemResponse();
            dto.itemCode = item.getItemCode().getValue();
            dto.quantity = item.getQuantity().getValue();
            dto.status = item.getStatus().name();
            return dto;
        }
    }

    // DTO для списання запасу
    public static class DecreaseStockRequest {
        public String itemCode;
        public int amount;
    }

    // (Простий runtime exception, можна замінити на ResponseStatusException)
    @ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String msg) { super(msg); }
    }
}