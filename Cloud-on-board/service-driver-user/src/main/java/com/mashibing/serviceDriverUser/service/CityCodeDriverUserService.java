package com.mashibing.serviceDriverUser.service;

import com.mashibing.serviceDriverUser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-19 19:51
 */
@Service
public class CityCodeDriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    public boolean ifWithDriver(String cityCode){

        int driverCount = driverUserMapper.selectDriverUserCountByCityCode(cityCode);

        if (driverCount > 0){
            return true;
        }else {
            return false;
        }
    }

}
