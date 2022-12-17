package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-17 15:15
 */
@RestController
@RequestMapping("Order")
@Slf4j
public class OrderController
{

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest){

        log.info(orderRequest.getAddress());

        return orderInfoService.addOrder(orderRequest);
    }

}
