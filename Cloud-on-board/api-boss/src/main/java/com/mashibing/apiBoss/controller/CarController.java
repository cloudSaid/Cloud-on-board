package com.mashibing.apiBoss.controller;

import com.mashibing.apiBoss.service.CarService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-10 16:37
 */
@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("addCar")
    public ResponseResult addCar(@RequestBody Car car){

        return carService.addCar(car);

    }


}
