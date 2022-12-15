package com.mashibing.apiDriver.service;

import com.mashibing.apiDriver.remote.MapClient;
import com.mashibing.apiDriver.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-16 2:38
 */
@Service
public class PointService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private MapClient mapClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest){

        //通过carid查询car信息
        Long carId = apiDriverPointRequest.getCarId();
        ResponseResult<Car> carResponseResult = serviceDriverUserClient.getCar(carId);
        Car carInfo = carResponseResult.getData();
        String tid = carInfo.getTid();
        String trid = carInfo.getTrid();

        //调用map的Point服务
        PointRequest pointRequest = new PointRequest();
        pointRequest.setPoints(apiDriverPointRequest.getPoints());
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        return mapClient.upload(pointRequest);
    }

}
