package org.example.Remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/2 17:30
 */
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient
{

    @RequestMapping(method = RequestMethod.POST,value = "/user")
    ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

    @RequestMapping(method = RequestMethod.GET,value = "/user/{phone}")
    ResponseResult getUser(@PathVariable String phone);
}
