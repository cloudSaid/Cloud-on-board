package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.responese.OrderDriverResponse;
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
    public boolean testDriver(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return driverService.testDriver(verificationCodeDTO.getDriverPhone());
    }

    @GetMapping("get-workable-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getWorkableDriver(@PathVariable("carId") long carId){
        return driverService.getWorkableDriver(carId);
    }

}
