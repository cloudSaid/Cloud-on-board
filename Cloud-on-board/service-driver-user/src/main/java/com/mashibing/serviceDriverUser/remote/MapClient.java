package com.mashibing.serviceDriverUser.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-14 19:16
 */
@FeignClient("service-map")
public interface MapClient {

    @RequestMapping(method = RequestMethod.POST,value = "addTerminal")
    ResponseResult<TerminalResponse> addTerminal(String terminalName);
}
