package com.mashibing.serviceprice.remote;


import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-map")
public interface MapClient {
    @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
    ResponseResult direction(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
