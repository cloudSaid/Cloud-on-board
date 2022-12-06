package com.mashibing.serviceprice.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.responese.ForecastPriceResponse;
import com.mashibing.serviceprice.remote.MapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO)
    {
        ResponseResult direction = mapClient.direction(forecastPriceDTO);
        log.info(direction.toString());

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(111.11);
        return ResponseResult.success(forecastPriceResponse);
    }
}
