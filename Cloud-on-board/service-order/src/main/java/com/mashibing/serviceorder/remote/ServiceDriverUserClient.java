package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
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
}
