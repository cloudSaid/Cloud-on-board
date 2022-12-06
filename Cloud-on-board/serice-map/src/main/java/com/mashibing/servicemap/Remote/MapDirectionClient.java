package com.mashibing.servicemap.Remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.responese.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.plugin.com.AmbientProperty;

/**
 * @author Leo
 * @version 1.0
 * @description: 调用地图接口
 * @date 2022-12-06 12:56
 */
@Service
@Slf4j
public class MapDirectionClient
{
    //output=xml&key=<用户的key>

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AmapConfigConstants.DIRECTION_URL);
        stringBuilder.append("?");
        stringBuilder.append("origin=" + depLongitude + "," + depLatitude);
        stringBuilder.append("&");
        stringBuilder.append("destination=" + destLongitude + "," + destLatitude);
        stringBuilder.append("&");
        stringBuilder.append("extensions=base");
        stringBuilder.append("&");
        stringBuilder.append("output=json&key=" + amapKey);

        System.out.println(stringBuilder.toString());
        //https://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=all&output=xml&key=<用户的key>
        //https://restapi.amap.com/v3/direction/driving?origins=116.508051,39.875399&destination116.604868,39.794463&extensions=base&output=json&key=2e471066e658217c93e47e4902c8a79d

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(stringBuilder.toString(), String.class);
        String directionEntityBody = directionEntity.getBody();
        log.info("解析路径...");
        return parseDirectionEntity(directionEntityBody);

    }

    private DirectionResponse parseDirectionEntity(String directionEntityBody)
    {
        DirectionResponse directionResponse = null;

        try {
            JSONObject result = JSONObject.fromObject(directionEntityBody);
            if (result.has(AmapConfigConstants.STATUS)){
                int start = result.getInt(AmapConfigConstants.STATUS);
                if (start == 1){
                    if (result.has(AmapConfigConstants.ROUTE)){
                        JSONObject route = result.getJSONObject(AmapConfigConstants.ROUTE);
                        JSONArray pathArray = route.getJSONArray(AmapConfigConstants.PATHS);
                        JSONObject fastestPath = pathArray.getJSONObject(0);
                        if (fastestPath.has(AmapConfigConstants.DISTANCE) && fastestPath.has(AmapConfigConstants.DURATION)){
                            //距离
                            directionResponse = new DirectionResponse();
                            int distance = fastestPath.getInt(AmapConfigConstants.DISTANCE);
                            int duration = fastestPath.getInt(AmapConfigConstants.DURATION);
                            directionResponse.setDuration(duration);
                            directionResponse.setDistance(distance);
                        }
                    }
                }
            }
        }catch (Exception e){
            log.info("路径解析失败");
            throw new RuntimeException("无法解析路径");
        }

        return directionResponse;
    }

}
