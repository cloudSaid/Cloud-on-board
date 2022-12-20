package com.mashibing.serviceDriverUser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceDriverUser.service.CityCodeDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-19 19:54
 */
@RestController
public class CityCodeDriverUserController {

    @Autowired
    private CityCodeDriverUserService cityCodeDriverUserService;

    @GetMapping("ifWithDriver")
    public ResponseResult<Boolean> ifWithDriver(@RequestParam String cityCode){
        boolean withDriver = cityCodeDriverUserService.ifWithDriver(cityCode);

        return ResponseResult.success(withDriver);
    }


}
