package com.mashibing.servicemap.Remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TerminalResponse;
import com.mashibing.internalcommon.responese.TrackResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-15 14:43
 */
@Service
public class TrackClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TrackResponse> addTrack(String tid){

        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TRACK_ADD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+sid);
        url.append("&");
        url.append("name="+tid);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null,String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String trid = data.getString("trid");
        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        return ResponseResult.success(trackResponse);
    }
}
