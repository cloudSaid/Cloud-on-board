package com.mashibing.servicemap.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.TrackResponse;
import com.mashibing.servicemap.Service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-15 14:42
 */
@RestController
public class TrackController {

    @Autowired
    private TrackService trackService;

    @RequestMapping("addTrack")
    public ResponseResult<TrackResponse> add(@RequestParam String tid){

       return trackService.add(tid);

    }

}
