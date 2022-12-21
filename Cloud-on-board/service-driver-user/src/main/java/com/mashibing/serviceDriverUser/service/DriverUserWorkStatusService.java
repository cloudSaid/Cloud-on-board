package com.mashibing.serviceDriverUser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-13 2:32
 */
@Service
public class DriverUserWorkStatusService {

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;


    public ResponseResult updateDriverStarts(Long driverId,Integer start){

        List<com.mashibing.internalcommon.dto.DriverUserWorkStatus> driverInfoArr = driverUserWorkStatusMapper
                .selectByMap((Map<String, Object>) new HashMap<String, Object>().put("driverId", driverId));

        com.mashibing.internalcommon.dto.DriverUserWorkStatus driverInfo = driverInfoArr.get(0);

        driverInfo.setWorkStatus(start);
        driverInfo.setGmtModified(LocalDateTime.now());

        driverUserWorkStatusMapper.updateById(driverInfo);

        return ResponseResult.success();
    }

    /**
     * 查询指定状态的司机
     * @param driverId
     * @param status
     * @return
     */
    public DriverUserWorkStatus selectDriverStarts(Long driverId, Integer status){
        return driverUserWorkStatusMapper.selectOne(new QueryWrapper<DriverUserWorkStatus>().
                eq("driver_id",driverId).eq("work_status",status));
    }

}
