package com.mashibing.apiDriver.service;

import com.mashibing.apiDriver.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateUser(DriverUser driverUser){
        return serviceDriverUserClient.updateUser(driverUser);
    }


}
