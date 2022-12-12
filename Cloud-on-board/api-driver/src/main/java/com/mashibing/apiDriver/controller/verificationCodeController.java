package com.mashibing.apiDriver.controller;
import com.mashibing.apiDriver.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.responese.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-12 13:02
 */
@RestController
public class verificationCodeController
{
    @Autowired
    private UserService userService;

    //司机端登陆发送验证ma
    @GetMapping("getVerificationCode")
    public ResponseResult getVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return userService.getVerificationCode(verificationCodeDTO);
    }
    @PostMapping("/verification-code-check")
    public ResponseResult<TokenResponse> checkCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return userService.checkCode(verificationCodeDTO);
    }

}
