package com.mashibing.servicemap.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.DirectionResponse;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 2:40
 */
@Service
public class DirectionService {
    /**
     * 请求地图服务获取距离和时间
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        DirectionResponse directionResponse = new DirectionResponse();
        //距离
        directionResponse.setDistance(100);
        //时间
        directionResponse.setDuration(60);

        return ResponseResult.success(directionResponse);
    }
}
