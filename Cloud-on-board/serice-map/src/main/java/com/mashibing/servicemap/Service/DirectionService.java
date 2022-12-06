package com.mashibing.servicemap.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.DirectionResponse;
import com.mashibing.servicemap.Remote.MapDirectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-06 2:40
 */
@Service
@Slf4j
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

    /**
     * 请求地图服务获取距离和时间
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
        log.info(direction.toString());
        return ResponseResult.success(direction);
    }
}
