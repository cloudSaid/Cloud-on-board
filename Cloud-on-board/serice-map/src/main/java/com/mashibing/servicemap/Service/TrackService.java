package com.mashibing.servicemap.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TrackResponse;
import com.mashibing.servicemap.Remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-15 14:42
 */
@Service
public class TrackService {

    @Autowired
    private TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){
        return trackClient.addTerminal(tid);
    }

}
