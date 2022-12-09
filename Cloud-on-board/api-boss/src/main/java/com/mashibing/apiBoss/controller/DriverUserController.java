package com.mashibing.apiBoss.controller;

import com.mashibing.apiBoss.service.DriverUserService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-09 16:22
 */
@RestController
public class DriverUserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/DriverUser")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser){

        return driverUserService.addDriverUser(driverUser);

    }

    @PutMapping("/DriverUser")
    public ResponseResult putDriverUser(@RequestBody DriverUser driverUser){

        return driverUserService.putDriverUser(driverUser);

    }


}
