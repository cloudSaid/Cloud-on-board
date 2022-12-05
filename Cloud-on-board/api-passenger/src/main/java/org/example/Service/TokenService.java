package org.example.Service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.responese.TokenResponse;
import com.mashibing.internalcommon.util.JWTUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/5 1:17
 */
@Service
public class TokenService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    public ResponseResult refreshToken(String refreshToken){
        //解析token
        TokenResult tokenResult = JWTUtils.checkToken(refreshToken);
        if(tokenResult == null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //生成TokenKey
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(tokenResult.getPhone(), tokenResult.getIdentity(), TokenConstants.REFRESH_TOKEN_TYPE);
        //校验token
        String redisRefreshTokenValue = stringRedisTemplate.opsForValue().get(refreshTokenKey);
        if (StringUtils.isBlank(redisRefreshTokenValue) || !refreshToken.equals(redisRefreshTokenValue))
        {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //校验无误则生成双token
        String refreshRefreshToken = JWTUtils.generatorToken(tokenResult.getPhone(), tokenResult.getIdentity(), TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JWTUtils.generatorToken(tokenResult.getPhone(), tokenResult.getIdentity(), TokenConstants.ACCESS_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(tokenResult.getPhone(), tokenResult.getIdentity(), TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshRefreshToken,31, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshRefreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }


}
