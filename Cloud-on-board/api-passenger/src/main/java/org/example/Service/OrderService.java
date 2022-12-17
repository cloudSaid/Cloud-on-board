package org.example.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.example.Remote.ServiceOrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-17 15:19
 */
@Service
public class OrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;


    public ResponseResult addOrder(OrderRequest orderRequest){
       return serviceOrderClient.addOrder(orderRequest);
    }

}
