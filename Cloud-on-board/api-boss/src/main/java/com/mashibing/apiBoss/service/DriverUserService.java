package com.mashibing.apiBoss.service;

import com.mashibing.apiBoss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-09 16:27
 */
@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient driverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser){
        return driverUserClient.addDriverUser(driverUser);
    }

    public ResponseResult putDriverUser(DriverUser driverUser){
        return driverUserClient.putDriverUser(driverUser);
    }
}
