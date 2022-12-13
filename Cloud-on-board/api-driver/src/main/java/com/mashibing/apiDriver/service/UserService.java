package com.mashibing.apiDriver.service;

import com.mashibing.apiDriver.remote.ServiceDriverUserClient;
import com.mashibing.apiDriver.remote.VerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import com.mashibing.internalcommon.responese.TokenResponse;
import com.mashibing.internalcommon.util.JWTUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    public ResponseResult getVerificationCode(VerificationCodeDTO verificationCodeDTO){
        //调用servicedriver验证该司机用户是否存在
        boolean testDriver = serviceDriverUserClient.testDriver(verificationCodeDTO);
        //存在则调用验证码生成验证码并发送存入redis
        if (!testDriver){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITST.getCode(),CommonStatusEnum.DRIVER_NOT_EXITST.getValue());
        }
        //生成验证码
        ResponseResult<NumberCodeResponse> numberCodeResponseResponseResult = verificationCodeClient.numberCode(6);
        int numberCode = numberCodeResponseResponseResult.getData().getNumberCode();
        //调用第三方接口
      log.info("调用第三方接口发送验证密码:" + numberCode);
      //存入redis
        stringRedisTemplate.opsForValue().set(RedisPrefixUtils
                .generatorKeyByPhone(verificationCodeDTO.getDriverPhone(),
                        IdentityConstants.DRIVER_IDENTITY),String.valueOf(numberCode),
                60, TimeUnit.MINUTES);

        return ResponseResult.success();


    }


    /**
     * 校验验证码
     * @param verificationCodeDTO
     * @return
     */
    public ResponseResult<TokenResponse> checkCode(VerificationCodeDTO verificationCodeDTO) {

        //获取到手机号和验证码
        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        //从redis中取出验证码
        String driverVerificationCodeKey = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstants.DRIVER_IDENTITY);

        String redisVerificationCode = stringRedisTemplate.opsForValue().get(driverVerificationCodeKey);

        if (StringUtils.isBlank(redisVerificationCode) || verificationCode.equalsIgnoreCase(redisVerificationCode)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        //缓存accessToken
        String accessTokenKey = RedisPrefixUtils
                .generatorTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);

        String accessToken = JWTUtils.generatorToken(driverPhone,
                IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        //缓存refreshToken
        String refreshTokenKey = RedisPrefixUtils
                .generatorTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshToken = JWTUtils.generatorToken(driverPhone,
                IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);


        //返回token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success().setData(tokenResponse);


    }
}
