package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-price")
public interface PriceClient {

    //判断城市是否可以下单
    @PostMapping("/price-rule/if-existence")
    ResponseResult<Boolean> ifExistence(@RequestBody PriceRule priceRule);

}
