package org.example.Service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import com.mashibing.internalcommon.responese.TokenResponse;
import com.mashibing.internalcommon.util.JWTUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.example.Remote.ServicePassengerUserClient;
import org.example.Remote.ServiceVerificationcodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/1 2:06
 */
@Service
@Slf4j
public class VerificationCodeService
{

    @Autowired
    private ServiceVerificationcodeClient verificationcodeClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult<NumberCodeResponse> generatorCode(String passengerPhone)
    {
        ResponseResult<NumberCodeResponse> responseResult = verificationcodeClient.numberCode(6);
        int numberCode = responseResult.getData().getNumberCode();
        log.info("调用服务获取验证码" + numberCode);
        //验证码存入redis
        stringRedisTemplate.opsForValue()
                .set(RedisPrefixUtils.verificationCodePrefix + passengerPhone
                        ,numberCode+"",120, TimeUnit.MINUTES);
        return responseResult;

    }


    public ResponseResult<TokenResponse> checkCode(VerificationCodeDTO verificationCodeDTO)
    {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("客户端验证码" + verificationCodeDTO.getVerificationCode());
        //从redis中取出验证码
        String verificationCode = stringRedisTemplate.opsForValue()
                .get(RedisPrefixUtils.verificationCodePrefix + passengerPhone);
        //验证码是否合法
        if (StringUtils.isBlank(verificationCode) || !verificationCode.equals(verificationCodeDTO.getVerificationCode()))
        {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //查询该用户是否注册
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);
        //缓存accessToken
        String accessTokenKey = RedisPrefixUtils
                .generatorTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String accessToken = JWTUtils.generatorToken(passengerPhone,
                IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);
        //缓存refreshToken
        String refreshTokenKey = RedisPrefixUtils
                .generatorTokenKey(passengerPhone, IdentityConstants.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshToken = JWTUtils.generatorToken(passengerPhone,
                IdentityConstants.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);


        //返回token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success().setData(tokenResponse);
    }



}
