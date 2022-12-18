package com.mashibing.serviceprice.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.responese.DirectionResponse;
import com.mashibing.internalcommon.responese.ForecastPriceResponse;
import com.mashibing.internalcommon.util.BigDecimalUtils;
import com.mashibing.serviceprice.mapper.PriceMapper;
import com.mashibing.serviceprice.remote.MapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 2:21
 */
@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private MapClient mapClient;

    @Autowired
    private PriceMapper priceMapper;

    public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO)
    {
        DirectionResponse directionResponse = mapClient.direction(forecastPriceDTO);
        log.info(directionResponse.toString());

         //距离
        Integer distance = directionResponse.getDistance();
        //时间
        Integer duration = directionResponse.getDuration();

        /**
         * 后期优化点
         */

        String cityCode = forecastPriceDTO.getCityCode();
        String vehicleType = forecastPriceDTO.getVehicleType();

        PriceRule priceRule = priceMapper.selectOne(new QueryWrapper<PriceRule>()
                .eq("fare_type", cityCode + "$" + vehicleType));

        double price = getPrice(distance, duration, priceRule);

        log.info("价格为" + price);

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(price);
        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据距离，时常，计价规则得出最后的价钱
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private double getPrice(Integer distance,Integer duration,PriceRule priceRule ){

        //获取起步价
        Double startFare = priceRule.getStartFare();
        double InitialPrice = BigDecimalUtils.add(OrderConstants.INITIAL_PRICE, startFare);

        //将距离米转为千米
        double distanceKm = BigDecimalUtils.divide(distance, OrderConstants.DISTANCE_DIVISOR);

        //起步价里程
        Double startMile = Double.valueOf(priceRule.getStartMile());
        double exceed = BigDecimalUtils.substract(distanceKm, startMile);
        double distanceDifference = exceed < 0 ? 0:exceed;

        //获得每公里单价
        Double unitPricePerMile = priceRule.getUnitPricePerMile();
        double multiply = BigDecimalUtils.multiply(distanceDifference, unitPricePerMile);

        //距离总价
        double totalPrice = BigDecimalUtils.add(InitialPrice, multiply);

        //时长转为分钟
        double minute = BigDecimalUtils.divide(duration, OrderConstants.MINUTE_DIVISOR);
        //每分钟计价
        Double unitPricePerMinute = priceRule.getUnitPricePerMinute();
        double timePrice = BigDecimalUtils.multiply(minute, unitPricePerMinute);
        //时长总价
        double total = BigDecimalUtils.add(totalPrice, timePrice);

        return total;
    }


}
