package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ImmediateOrderTo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-21 2:10
 */
@RestController
public class TestController {

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("immediateOrder")
    public ResponseResult immediateOrder(@RequestBody ImmediateOrderTo immediateOrderTo){
        orderInfoService.immediateOrder(immediateOrderTo);
        return ResponseResult.success();
    }

}
