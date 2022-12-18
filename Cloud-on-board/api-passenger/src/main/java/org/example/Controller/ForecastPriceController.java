package org.example.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.example.Service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 1:35
 */
@RestController
@Slf4j
public class ForecastPriceController
{
    @Autowired
    private ForecastPriceService forecastPriceService;

    /**
     * 计算价格
     * @param forecastPriceDTO
     * @return
     */
    @RequestMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO)
    {
        log.info(forecastPriceDTO.toString());
        return forecastPriceService.forecastPrice(forecastPriceDTO);
    }

}
