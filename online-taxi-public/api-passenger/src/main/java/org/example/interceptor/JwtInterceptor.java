package org.example.interceptor;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JWTUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/2 23:00
 */
public class JwtInterceptor implements HandlerInterceptor
{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        TokenResult tokenResult = JWTUtils.checkToken(token);
        if (tokenResult == null){
           result = false;
           resultString = "无效token";
        }

        String accessTokenKey = RedisPrefixUtils
                    .generatorTokenKey(tokenResult.getPhone(), tokenResult.getIdentity(), TokenConstants.ACCESS_TOKEN_TYPE);
            String accessTokenValue = stringRedisTemplate.opsForValue().get(accessTokenKey);

        if (StringUtils.isBlank(accessTokenValue) || !token.trim().equals(accessTokenValue))
            {
                resultString = "token 已过期";
                result = false;
            }


        if (!result)
        {
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(resultString).toString()));
        }


        return result;
    }



}
