package org.example.Controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.attribute.UserPrincipal;

/**
 * @author Leo
 * @version 1.0
 * @description: TODO
 * @date 2022/12/5 12:48
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUserByPhone")
    public ResponseResult user(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        return userService.getUserByAccessToken(accessToken);
    }
}
