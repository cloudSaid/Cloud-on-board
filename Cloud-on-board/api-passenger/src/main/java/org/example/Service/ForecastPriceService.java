package org.example.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 1:43
 */
@Service
@Slf4j
public class ForecastPriceService
{

    /**
     * 计价
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude)
    {
      log.info(depLongitude);
      log.info(depLatitude);
      log.info(destLongitude);
      log.info(destLatitude);
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(111.11);
        return ResponseResult.success(forecastPriceResponse);
    }
}