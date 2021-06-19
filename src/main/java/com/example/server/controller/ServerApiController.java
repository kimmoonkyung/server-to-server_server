package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/server")
@Slf4j
public class ServerApiController {

//    @GetMapping("/hello")
//    public User hello(){
//        User user = new User();
//        user.setName("norotoo");
//        user.setAge(15);
//        return user;
//    }

    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}/test/{test}")
    public User post(@RequestBody User user,
                     @PathVariable int userId,
                     @PathVariable String userName,
                     @PathVariable String test){
        log.info("user: {}", user);
        log.info("userId: {}, userName: {}, test: {}", userId, userName, test);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User exchange(@RequestBody User user,
                     @PathVariable int userId,
                     @PathVariable String userName,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader){
        log.info("user: {}", user);
        log.info("userId: {}, userName: {}", userId, userName);
        log.info("authorization: {}, customHeader: {}", authorization, customHeader);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}/reqTest/{reqTest}")
    public Req<User> reqPost(
//                         HttpEntity<String> entity,
                         @RequestBody Req<User> user,
                         @PathVariable int userId,
                         @PathVariable String userName,
                         @PathVariable String reqTest,
                         @RequestHeader("x-authorization") String authorization,
                         @RequestHeader("custom-header") String customHeader){

//        log.info("req: {}", entity.getBody());
        log.info("user: {}", user);
        log.info("userId: {}, userName: {}, reqTest: {}", userId, userName, reqTest);
        log.info("authorization: {}, customHeader: {}, reqTest: {}", authorization, customHeader, reqTest);

        Req<User> response = new Req<>();
        response.setHeader(new Req.Header());
        response.setResponseBody(user.getResponseBody());
//        response.setResponseBody(null);

        return response;
    }

}
