package org.example.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TokenResponse;
import jdk.nashorn.internal.parser.Lexer;
import org.bouncycastle.cert.crmf.RegTokenControl;
import org.example.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/5 1:16
 */
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping("refreshToken")
    public ResponseResult<TokenResponse> refreshToken(@RequestBody TokenResponse tokenResponse){
        return tokenService.refreshToken(tokenResponse.getRefreshToken());
    }
}
