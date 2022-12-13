package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.service.DriverUserWorkStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-13 2:50
 */
@RestController
public class DriverUserWorkStatusController {

    @Autowired
    private DriverUserWorkStatusService driverUserWorkStatusService;

    /**
     * 修改司机状态
     * @param driverUserWorkStatus
     * @return
     */
    @PutMapping("driverUserWorkStatus")
    public ResponseResult driverUserWorkStatus(@RequestBody DriverUserWorkStatus driverUserWorkStatus){
        return driverUserWorkStatusService.updateDriverStarts(driverUserWorkStatus.getDriverId(),driverUserWorkStatus.getWorkStatus());
    }

}
