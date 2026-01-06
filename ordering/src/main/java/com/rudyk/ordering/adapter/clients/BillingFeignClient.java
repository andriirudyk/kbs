package com.rudyk.ordering.adapter.clients;


import com.rudyk.ordering.api.dto.PaymentRequest;
import com.rudyk.ordering.api.dto.PaymentResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "billingservice")
public interface BillingFeignClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/api/billing/payments",
            consumes ="application/json")
   public PaymentResponse charge(@RequestBody PaymentRequest request);

}
