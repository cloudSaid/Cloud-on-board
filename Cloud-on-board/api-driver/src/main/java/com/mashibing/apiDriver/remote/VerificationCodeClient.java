package com.mashibing.apiDriver.remote;


import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-verificationcode")
public interface VerificationCodeClient {

    @RequestMapping("/numberCode/{size}")
    ResponseResult<NumberCodeResponse> numberCode(@PathVariable("size") int size);

}
