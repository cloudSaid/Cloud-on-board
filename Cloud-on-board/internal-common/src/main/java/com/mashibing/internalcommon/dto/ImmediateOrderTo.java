package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-21 1:11
 */
@Data
public class ImmediateOrderTo {
    /**
     * 预计出发地点经度
     */
    private String depLongitude;

    /**
     * 预计出发地点纬度
     */
    private String depLatitude;
}
