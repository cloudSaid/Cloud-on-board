package com.mashibing.servicemap.Remote;

import com.mashibing.internalcommon.constant.AmapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.ServiceResponse;
import com.mashibing.internalcommon.responese.TerminalResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-14 18:23
 */
@Service
@Slf4j
public class TerminalClient {

    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String sid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult<TerminalResponse> addTerminal(String TerminalName , String desc){

        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_ADD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+sid);
        url.append("&");
        url.append("name="+TerminalName);
        url.append("&");
        url.append("desc="+desc);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null,String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        String tid = data.getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return ResponseResult.success(terminalResponse);
    }

    public ResponseResult<List<TerminalResponse>> aroundsearch(String center,Integer radius){
        StringBuilder url = new StringBuilder();
        url.append(AmapConfigConstants.TERMINAL_ADD);
        url.append("?");
        url.append("key="+amapKey);
        url.append("&");
        url.append("sid="+sid);
        url.append("&");
        url.append("center="+center);
        url.append("&");
        url.append("radius="+radius);

        ResponseEntity<String> forEntity = restTemplate.postForEntity(url.toString(), null,String.class);
        String body = forEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        JSONObject data = result.getJSONObject("data");
        if (data.isNullObject()){
            List<TerminalResponse> objects = new ArrayList<>();
            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setTid("1");
            return ResponseResult.success(objects);
        }
        JSONArray results = data.getJSONArray("results");


        List<TerminalResponse> terminalResponseArrayList = new ArrayList<>();



        for (int i = 0;i < results.size();i++){
            TerminalResponse terminalResponse = new TerminalResponse();
            JSONObject carResultInfo = results.getJSONObject(i);
            long carId = carResultInfo.getLong("desc");
            String tid = carResultInfo.getString("tid");
            terminalResponse.setCarId(carId);
            terminalResponse.setTid(tid);
            terminalResponseArrayList.add(terminalResponse);
        }

        return ResponseResult.success(terminalResponseArrayList);
    }


}
