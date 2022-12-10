package com.mashibing.serviceDriverUser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-10 13:46
 */
@Service
public class CarService
{

    @Autowired
    private CarMapper carMapper;

    public ResponseResult addCar(Car car)
    {
        LocalDateTime now = LocalDateTime.now();

        car.setGmtCreate(now);
        car.setGmtModified(now);

        carMapper.insert(car);

        return ResponseResult.success("");
    }

}
