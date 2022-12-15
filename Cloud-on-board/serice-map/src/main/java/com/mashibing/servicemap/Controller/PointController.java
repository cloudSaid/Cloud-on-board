package com.mashibing.servicemap.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.servicemap.Service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-16 1:46
 */
@RestController
public class PointController {

    @Autowired
    private PointService pointService;

    @PostMapping("carPoint")
    public ResponseResult upload(@RequestBody PointRequest pointRequest){

        return pointService.upload(pointRequest);

    }

}
