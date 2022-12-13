package com.mashibing.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import com.mashibing.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
