package org.example.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import com.mashibing.internalcommon.responese.TokenResponse;
import org.example.Service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/1 1:56
 */
@RestController
public class verificationCodeController {

    @Autowired
    private VerificationCodeService codeService;

    @RequestMapping("/verificationCode/getVerificationCode")
    public ResponseResult<NumberCodeResponse> getVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return codeService.generatorCode(verificationCodeDTO.getPassengerPhone());
    }


    @PostMapping("/verification-code-check")
    public ResponseResult<TokenResponse> checkCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return codeService.checkCode(verificationCodeDTO);
    }
}
