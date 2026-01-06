package com.rudyk.catalog.services;

import com.rudyk.catalog.domain.model.MenuItem;
import com.rudyk.catalog.repositories.MenuRepository;
import com.rudyk.catalog.domain.model.Menu;
import com.rudyk.catalog.domain.model.MenuStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Menu> getActiveMenu() {
        LocalDateTime now = LocalDateTime.now();
        List<Menu> menus = menuRepository.findByStatusAndValidFromBeforeAndValidToAfter(
                MenuStatus.PUBLISHED, now, now
        );
        // If multiple, pick latest validFrom
        return menus.stream()
                .max(Comparator.comparing(Menu::getValidFrom));
    }

    @Transactional(readOnly = true)
    public Optional<MenuItem> findItemInActiveMenu(String code) {
        return getActiveMenu()
                .flatMap(menu -> menu.getItems().stream()
                        .filter(it -> it.getItemCode().getCode().equalsIgnoreCase(code))
                        .findFirst()
                );
    }

    @Transactional(readOnly = true)
    public List<MenuItem> findItemsInActiveMenu(List<String> codes) {
        return getActiveMenu()
                .map(menu -> menu.getItems().stream()
                        .filter(it -> codes.stream()
                                .anyMatch(c -> c.equalsIgnoreCase(it.getItemCode().getCode())))
                        .collect(Collectors.toList()))
                .orElseGet(java.util.Collections::emptyList);
    }

    @Transactional(readOnly = true)
    public Optional<MenuItem> findItemInMenu(Long menuId, String code) {
        return getMenu(menuId)
                .flatMap(menu -> menu.getItems().stream()
                        .filter(it -> it.getItemCode().getCode().equalsIgnoreCase(code))
                        .findFirst()
                );
    }

    @Transactional(readOnly = true)
    public Optional<Menu> getMenu(Long id) {
        return menuRepository.findById(id);
    }
}