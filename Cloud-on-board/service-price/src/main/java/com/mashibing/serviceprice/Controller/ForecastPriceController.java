package com.mashibing.serviceprice.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.responese.ForecastPriceResponse;
import com.mashibing.serviceprice.Service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 2:20
 */
@RestController
@RequestMapping("/forecast")
public class ForecastPriceController
{
    @Autowired
    private ForecastPriceService forecastPriceService;

    @RequestMapping("/price")
    public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO)
    {
        //获取计算完的额价格
        ResponseResult<ForecastPriceResponse> priceResult = forecastPriceService.forecastPrice(forecastPriceDTO);

        return priceResult;
    }

}
