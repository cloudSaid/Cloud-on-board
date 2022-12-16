package com.mashibing.serviceDriverUser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TerminalResponse;
import com.mashibing.internalcommon.responese.TrackResponse;
import com.mashibing.serviceDriverUser.mapper.CarMapper;
import com.mashibing.serviceDriverUser.remote.MapClient;
import org.apache.commons.lang.StringUtils;
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
    private MapClient mapClient;

    @Autowired
    private CarMapper carMapper;

    public ResponseResult addCar(Car car)
    {
        LocalDateTime now = LocalDateTime.now();

        car.setGmtCreate(now);
        car.setGmtModified(now);

        //通过地图服务注册车辆获取其tid
        ResponseResult<TerminalResponse> responseResult = mapClient.addTerminal(car.getVehicleNo() , car.getId().toString());
        TerminalResponse terminalResponse = responseResult.getData();
        String tid = terminalResponse.getTid();

        //通过tid创建轨迹获得trid
        ResponseResult<TrackResponse> trackResponseResponseResult = mapClient.add(tid);
        TrackResponse trackResponse = trackResponseResponseResult.getData();
        String trid = trackResponse.getTrid();

        car.setTrid(trid);
        car.setTid(tid);
        carMapper.insert(car);

        return ResponseResult.success("");
    }

    public ResponseResult<Car> getCar(Long carId) {

        return ResponseResult.success(carMapper.selectById(carId));

    }
}
