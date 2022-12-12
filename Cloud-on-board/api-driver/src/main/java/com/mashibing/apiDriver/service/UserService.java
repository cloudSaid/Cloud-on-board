package com.mashibing.apiDriver.service;

import com.mashibing.apiDriver.remote.ServiceDriverUserClient;
import com.mashibing.apiDriver.remote.VerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private VerificationCodeClient verificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public ResponseResult updateUser(DriverUser driverUser){
        return serviceDriverUserClient.updateUser(driverUser);
    }

    public ResponseResult getVerificationCode(String phone){
        //调用servicedriver验证该司机用户是否存在
        boolean testDriver = serviceDriverUserClient.testDriver(phone);
        //存在则调用验证码生成验证码并发送存入redis
        if (!testDriver){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITST);
        }
        //生成验证码
        ResponseResult<NumberCodeResponse> numberCodeResponseResponseResult = verificationCodeClient.numberCode(6);
        int numberCode = numberCodeResponseResponseResult.getData().getNumberCode();
        //调用第三方接口
      log.info("调用第三方接口发送验证密码:" + numberCode);
      //存入redis
        stringRedisTemplate.opsForValue().set(RedisPrefixUtils.generatorKeyByPhone(phone, IdentityConstants.DRIVER_IDENTITY),String.valueOf(numberCode),60, TimeUnit.MINUTES);
        //返回token

        return ResponseResult.success();


    }





}
