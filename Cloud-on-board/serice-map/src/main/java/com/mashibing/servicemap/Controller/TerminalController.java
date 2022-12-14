package com.mashibing.servicemap.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TerminalResponse;
import com.mashibing.servicemap.Remote.TerminalClient;
import com.mashibing.servicemap.Service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-14 18:22
 */
@RestController
public class TerminalController {

    @Autowired
    private TerminalService terminalClient;

    @PostMapping("addTerminal")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam("terminalName") String terminalName){
        return terminalClient.addTerminal(terminalName);
    }

}
