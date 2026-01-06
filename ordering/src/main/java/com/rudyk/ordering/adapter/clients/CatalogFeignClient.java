package com.rudyk.ordering.adapter.clients;

import com.rudyk.ordering.api.dto.MenuDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("catalogservice")
public interface CatalogFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/api/menus/active",
            consumes="application/json")
    public MenuDto getActiveMenu();
}
