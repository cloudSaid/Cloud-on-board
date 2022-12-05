package org.example.Service;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JWTUtils;
import org.example.Remote.ServicePassengerUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/5 12:50
 */
@Service
public class UserService
{

   @Autowired
   private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken)
    {
        //通过手机号查询用户信息
        TokenResult tokenResult = JWTUtils.parseToken(accessToken);
        String userPhone = tokenResult.getPhone();
        ResponseResult passengerUser = servicePassengerUserClient.getUser(userPhone);
        return ResponseResult.success(passengerUser);
    }
}
