package org.example.Remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-17 15:18
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping (method = RequestMethod.POST,value = "order/add")
    ResponseResult addOrder(@RequestBody OrderRequest orderRequest);

}
