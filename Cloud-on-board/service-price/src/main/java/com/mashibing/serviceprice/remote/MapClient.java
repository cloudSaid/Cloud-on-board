package com.mashibing.serviceprice.remote;


import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-map")
public interface MapClient {
    @RequestMapping(value = "direction")
    ResponseResult direction(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
