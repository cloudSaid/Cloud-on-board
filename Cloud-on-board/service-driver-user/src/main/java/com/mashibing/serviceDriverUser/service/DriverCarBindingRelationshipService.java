package com.mashibing.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-10 14:24
 */
@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult binding(DriverCarBindingRelationship driverCarBindingRelationship){

        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_start",DriverCarConstants.DRIVER_CAR_BIND);
        Integer driverBindCarCount = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (driverBindCarCount.intValue() > 0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS);
        }
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        Integer selectCount = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (selectCount.intValue() > 0){
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS);
        }
        queryWrapper.clear();
        queryWrapper.eq("bind_start",DriverCarConstants.DRIVER_CAR_BIND);
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        Integer carBindDriverCount = driverCarBindingRelationshipMapper.selectCount(queryWrapper);

        if (carBindDriverCount.intValue() > 0){
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS);
        }




        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);

        return ResponseResult.success("");
    }




    public ResponseResult unbinding(DriverCarBindingRelationship driverCarBindingRelationship){

        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id",driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id",driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        List<DriverCarBindingRelationship> driverBindCatInfoArr = driverCarBindingRelationshipMapper.selectList(queryWrapper);
        if (driverBindCatInfoArr == null){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS);
        }

        DriverCarBindingRelationship driverBindCatInfo = driverBindCatInfoArr.get(0);
        driverBindCatInfo.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        driverBindCatInfo.setUnBindingTime(LocalDateTime.now());

        driverCarBindingRelationshipMapper.updateById(driverBindCatInfo);

        return ResponseResult.success("");

    }

    public DriverCarBindingRelationship getDriverUseCarId(long carId){
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id",carId);
        queryWrapper.eq("bind_state",DriverCarConstants.DRIVER_CAR_BIND);
        return driverCarBindingRelationshipMapper.selectOne(queryWrapper);
    }

}
