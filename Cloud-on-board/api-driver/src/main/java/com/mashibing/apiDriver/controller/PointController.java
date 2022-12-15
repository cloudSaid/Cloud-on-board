package com.mashibing.apiDriver.controller;

import com.mashibing.apiDriver.service.PointService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-16 2:35
 */
@RestController
public class PointController {

    @Autowired
    private PointService pointService;

    @RequestMapping("upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest){

        return pointService.upload(apiDriverPointRequest);
    }

}
