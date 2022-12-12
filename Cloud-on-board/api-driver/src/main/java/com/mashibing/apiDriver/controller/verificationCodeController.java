package com.mashibing.apiDriver.controller;
import com.mashibing.apiDriver.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseResult getVerificationCode(String phone){
        return userService.getVerificationCode(phone);
    }


}
