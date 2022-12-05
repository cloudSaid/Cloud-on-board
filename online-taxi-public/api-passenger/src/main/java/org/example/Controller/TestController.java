package org.example.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/1 1:38
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "runing !!!";
    }
}
