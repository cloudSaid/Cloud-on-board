package com.mashibing.servicemap.Remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Leo
 * @version 1.0
 * @description: 增加高德地图服务
 * @date 2022-12-14 17:28
 */

@Service
@Slf4j
public class ServiceClient {
    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult add(String name){
        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.SERVICE_ADD_URL);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("name="+name);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null,String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String sid = data.getString("sid");
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSid(sid);

        return ResponseResult.success(serviceResponse);
    }
}
