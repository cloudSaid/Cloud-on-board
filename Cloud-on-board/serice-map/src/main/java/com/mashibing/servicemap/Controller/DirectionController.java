package com.mashibing.servicemap.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.servicemap.Service.DirectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 2:40
 */
@RestController
@Slf4j
public class DirectionController {
    @Autowired
    private DirectionService directionService;

    @RequestMapping("direction")
    public ResponseResult direction(@RequestBody ForecastPriceDTO forecastPriceDTO){
        //出发点经纬度
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        //目的地经纬度
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        
        ResponseResult directionResponseResult = directionService.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        log.info(directionResponseResult.toString());
        return directionResponseResult;
    }
}
