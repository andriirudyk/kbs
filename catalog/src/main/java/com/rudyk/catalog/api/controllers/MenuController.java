package com.rudyk.catalog.api.controllers;

import com.rudyk.catalog.api.dtos.MenuDto;
import com.rudyk.catalog.api.dtos.MenuItemDto;
import com.rudyk.catalog.domain.model.Menu;
import com.rudyk.catalog.domain.model.MenuItem;
import com.rudyk.catalog.services.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/active")
    public MenuDto getActiveMenu() {
        return menuService.getActiveMenu()
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("No active menu"));
    }

    @GetMapping("/{id}")
    public MenuDto getMenu(@PathVariable Long id) {
        return menuService.getMenu(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    private MenuDto toDto(Menu menu) {
        MenuDto dto = new MenuDto();
        dto.id = menu.getId();
        dto.status = menu.getStatus().name();
        dto.validFrom = menu.getValidFrom().toString();
        dto.validTo = menu.getValidTo().toString();
        dto.items = menu.getItems().stream().map(this::toDto).collect(java.util.stream.Collectors.toList());
        return dto;
    }
    @GetMapping("/items/{code}")
    public MenuItemDto getActiveItemByCode(@PathVariable String code) {
        return menuService.findItemInActiveMenu(code)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Item not found in active menu: " + code));
    }

    @GetMapping("/items")
    public List<MenuItemDto> getActiveItemsByCodes(@RequestParam("codes") List<String> codes) {
        return menuService.findItemsInActiveMenu(codes).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/{menuId}/items/{code}")
    public MenuItemDto getItemByCodeFromMenu(@PathVariable Long menuId, @PathVariable String code) {
        return menuService.findItemInMenu(menuId, code)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Item not found in menu " + menuId + ": " + code));
    }

    private MenuItemDto toDto(MenuItem item) {
        MenuItemDto dto = new MenuItemDto();
        dto.id = item.getId();
        dto.itemCode = item.getItemCode().getCode();
        dto.displayName = item.getDisplayName();
        dto.description = item.getDescription();
        dto.categoryCode = item.getCategory().getCode();
        dto.categoryName = item.getCategory().getDisplayName();
        dto.basePrice = item.getBasePrice().getAmount().toString();
        dto.promoPrice = item.getPromoPrice() == null ? null :
                item.getPromoPrice().getAmount() + " " + item.getPromoPrice().getCurrency();
        dto.available = item.isAvailable();
        return dto;
    }
}