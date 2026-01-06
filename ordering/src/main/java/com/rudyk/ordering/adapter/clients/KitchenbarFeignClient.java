package com.rudyk.ordering.adapter.clients;


import com.rudyk.ordering.api.dto.CreateTicketRequest;
import com.rudyk.ordering.api.dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "kitchenbarservice")
public interface KitchenbarFeignClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/kitchenbar/tickets",
            consumes ="application/json")
    public TicketResponse create(@RequestBody CreateTicketRequest request);

}
