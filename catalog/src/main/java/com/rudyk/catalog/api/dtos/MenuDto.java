package com.rudyk.catalog.api.dtos;
import java.util.List;

public class MenuDto {
    public Long id;
    public String status;
    public String validFrom;
    public String validTo;
    public List<MenuItemDto> items;
}
