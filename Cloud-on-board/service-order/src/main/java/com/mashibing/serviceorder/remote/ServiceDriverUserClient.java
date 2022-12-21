package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-19 20:07
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {


    @RequestMapping(method = RequestMethod.GET,value = "ifWithDriver")
    public ResponseResult<Boolean> ifWithDriver(@RequestParam String cityCode);

    @RequestMapping(method = RequestMethod.GET,value = "get-workable-driver/{carId}")
    ResponseResult<OrderDriverResponse> getWorkableDriver(@PathVariable("carId") long carId);
}
