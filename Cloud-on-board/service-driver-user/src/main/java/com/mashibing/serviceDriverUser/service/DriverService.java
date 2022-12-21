package com.mashibing.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.OrderDriverResponse;
import com.mashibing.serviceDriverUser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import com.mashibing.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Driver;
import java.time.LocalDateTime;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-09 15:51
 */
@Service
public class DriverService {

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult addUser(DriverUser driverUser){

        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);
        //初始化司机工作状态数据
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatus.setGmtCreate(now);

        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success();

    }

    public ResponseResult putUser(DriverUser driverUser) {

        driverUser.setGmtModified(LocalDateTime.now());

        driverUserMapper.updateById(driverUser);

        return ResponseResult.success();
    }

    public boolean testDriver(String phone){

        Integer testDrivers = driverUserMapper.selectCount(new QueryWrapper<DriverUser>().eq("driver_phone", phone));

        if (testDrivers <= 0){
            return false;
        }

        return true;
    }

    @Autowired
    DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @Autowired
    DriverUserWorkStatusService driverUserWorkStatusService;

    /**
     * 通过carid查询可接单司机
     * @param carId
     * @return
     */
    public ResponseResult<OrderDriverResponse> getWorkableDriver(long carId){

        DriverCarBindingRelationship driverUseCarId = driverCarBindingRelationshipService.getDriverUseCarId(carId);
        if (driverUseCarId == null){
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),
                                        CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());
        }
        Long driverId = driverUseCarId.getDriverId();

        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusService.
                selectDriverStarts(driverId, DriverCarConstants.DRIVER_WORK_STATUS_START);

        if (driverUserWorkStatus == null){
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),
                    CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());
        }

        DriverUser driverUser = driverUserMapper.selectById(driverUserWorkStatus.getDriverId());

        if (driverUser == null){
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),
                    CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());
        }

        OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
        orderDriverResponse.setDriverId(driverUser.getId());
        orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());
        orderDriverResponse.setCarId(carId);
        orderDriverResponse.setLicenseId(driverUser.getLicenseId());

        return ResponseResult.success(orderDriverResponse);
    }
}
