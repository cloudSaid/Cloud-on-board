package org.example.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.example.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-17 14:59
 */
@RestController
@RequestMapping("order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest)
    {
        return orderService.addOrder(orderRequest);
    }

}
