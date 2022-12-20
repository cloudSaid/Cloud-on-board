package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.PriceClient;
import com.mashibing.serviceorder.remote.ServiceDriverUserClient;
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

    @Autowired
    private PriceClient priceClient;


    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

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

        //判断当前地区是否可以下单
        String fareType = orderRequest.getFareType();
        String[] split = fareType.split("$");
        PriceRule priceRule = new PriceRule();
        String cityCode = split[0];
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(split[1]);
        ResponseResult<Boolean> result = priceClient.ifExistence(priceRule);
        boolean ifExistence = result.getData().booleanValue();
        if (!ifExistence){
            return ResponseResult.fail(CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getCode(),CommonStatusEnum.CITY_SERVICE_NOT_SERVICE.getValue());
        }

        //当前地区是否有司机
        ResponseResult<Boolean> withDriver = serviceDriverUserClient.ifWithDriver(cityCode);
        if (!withDriver.getData()){
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(),CommonStatusEnum.CITY_DRIVER_EMPTY.getValue());
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
