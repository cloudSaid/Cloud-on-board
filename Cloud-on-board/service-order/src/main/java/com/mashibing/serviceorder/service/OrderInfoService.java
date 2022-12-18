package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-18 1:28
 */
@Service
public class OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    public ResponseResult addOrder(OrderRequest orderRequest){

        //判断设备是否在黑名单

        String deviceCode = RedisPrefixUtils.blackDeviceCodePrefix + orderRequest.getDeviceCode();
        Boolean hasKey = stringRedisTemplate.hasKey(deviceCode);
        if (hasKey){

            String sequipment = stringRedisTemplate.opsForValue().get(deviceCode);
            Integer integer = Integer.valueOf(sequipment);
            if (integer > 2){
                return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(),CommonStatusEnum.DEVICE_IS_BLACK.getValue());
            }else {
                stringRedisTemplate.opsForValue().increment(deviceCode);
            }
        }else {
            stringRedisTemplate.opsForValue().set(deviceCode,"1",1L, TimeUnit.DAYS);
        }

        //判断是否可以下单
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("passenger_id",orderRequest.getPassengerId());
        orderInfoQueryWrapper.and(queryWrapper -> queryWrapper.eq("order_status",OrderConstants.ORDER_START)
                .or().eq("order_status",OrderConstants.DRIVER_RECEIVE_ORDER)
                .or().eq("order_status",OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                .or().eq("order_status",OrderConstants.PICK_UP_PASSENGER)
                .or().eq("order_status",OrderConstants.PASSENGER_GETOFF)
                .or().eq("order_status",OrderConstants.TO_START_PAY)
        );

        Integer integer = orderInfoMapper.selectCount(orderInfoQueryWrapper);

        if (integer > 0){
            ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),CommonStatusEnum.ORDER_GOING_ON.getValue());
        }

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest,orderInfo);

        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setOrderTime(now);

        orderInfoMapper.insert(orderInfo);

        return ResponseResult.success("");
    }

}
