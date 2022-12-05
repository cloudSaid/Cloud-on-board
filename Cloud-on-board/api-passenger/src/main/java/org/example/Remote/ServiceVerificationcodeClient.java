package org.example.Remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/1 18:21
 */
@FeignClient("service-verificationcode")
public interface ServiceVerificationcodeClient
{

    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponse> numberCode(@PathVariable("size") int size);
}
