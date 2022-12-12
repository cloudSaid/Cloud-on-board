package com.mashibing.apiDriver.remote;

import com.mashibing.internalcommon.dto.*;
import com.mashibing.internalcommon.responese.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/driverUser")
    ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.GET,value = "testDriver")
    boolean testDriver(String phone);

}
