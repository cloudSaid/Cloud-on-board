package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-09 15:50
 */
@RestController
public class UserController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/DriverUser")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){
        return driverService.addUser(driverUser);
    }

    @PutMapping("/DriverUser")
    public ResponseResult putDriverUser(@RequestBody DriverUser driverUser){
        return driverService.putUser(driverUser);
    }

    @GetMapping("testDriver")
    public boolean testDriver(String phone){
        return driverService.testDriver(phone);
    }

}
