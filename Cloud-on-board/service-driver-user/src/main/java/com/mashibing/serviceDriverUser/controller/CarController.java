package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-10 13:46
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("addCar")
    public ResponseResult addCar(@RequestBody Car car){
        return carService.addCar(car);
    }

    @GetMapping("getCar")
    public ResponseResult<Car> getCar(@PathVariable Long carId){
        return carService.getCar(carId);
    }


}
