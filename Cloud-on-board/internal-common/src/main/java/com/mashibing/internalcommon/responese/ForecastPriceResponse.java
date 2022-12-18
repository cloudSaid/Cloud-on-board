package com.mashibing.internalcommon.responese;

import lombok.Data;

@Data
public class ForecastPriceResponse {

    private double price;

    /*
    * 城市代码
    * */
    private String cityCode;

    /*
    * 车辆类型
    * */
    private String vehicleType;

    /*
    * 颜色
    * */
    private String fareType;

    /*
    * 计价版本
    * */
    private Integer fareVersion;
}
