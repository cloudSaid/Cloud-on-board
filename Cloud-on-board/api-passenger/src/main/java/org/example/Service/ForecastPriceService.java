package org.example.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.responese.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.Remote.PriceClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PriceClient priceClient;

    /**
     * 计价
     * @return
     */
    public ResponseResult forecastPrice(ForecastPriceDTO forecastPriceDTO)
    {

        //调用计价服务获取价格
        ResponseResult<ForecastPriceResponse> forecastPriceResponseResponseResult = priceClient.forecastPrice(forecastPriceDTO);
        ForecastPriceResponse forecastPriceResponse = forecastPriceResponseResponseResult.getData();

        return ResponseResult.success(forecastPriceResponse);
    }
}
