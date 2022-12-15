package com.mashibing.servicemap.Service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.servicemap.Remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022-12-16 1:53
 */
@Service
public class PointService {

    @Autowired
    private PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest){

        return pointClient.upload(pointRequest);
    }

}
