package com.mashibing.apiBoss.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-09 16:23
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {


    @RequestMapping(method = RequestMethod.POST,value = "/DriverUser")
    ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.PUT,value = "/DriverUser")
    ResponseResult putDriverUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.POST,value = "addCar")
    ResponseResult addCar(@RequestBody Car car);

    @RequestMapping(method = RequestMethod.POST,value = "bind")
    ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

    @RequestMapping(method = RequestMethod.POST,value = "unbind")
    ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

}
