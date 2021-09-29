package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

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

    /*
     https://openapi.naver.com/v1/search/local.json
     ?query=%EC%A3%BC%EC%8B%9D
     &display=10
     &start=1
     &sort=random
     */
    @GetMapping("/naver")
    public ResponseEntity<String> naver(@RequestParam String keyword) {
        //http://localhost:9090/api/server/naver?keyword=방배준오헤
        //http://localhost:9090/api/server/naver?keyword=%EB%B0%A9%EB%B0%B0%EC%A4%80%EC%98%A4%ED%97%A4%EC%96%B4
//        String query = "산본롯데피트인";
//        String encode = Base64.getEncoder().encodeToString(query.getBytes(StandardCharsets.UTF_8));

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", keyword)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(StandardCharsets.UTF_8)
                .encode()
                .build()
                .toUri();

        log.info("naver search uri: {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "Cc2S153b3YTw22dnxJru")
                .header("X-Naver-Client-Secret", "o1Xx7Fn_nz")
                .build();
        log.info("naver search RequestEntity: {}",  req);

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result;

    }

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
        log.info("REQUEST user: {}", user);
        log.info("REQUEST userId: {}, userName: {}, test: {}", userId, userName, test);
        user.setAge(99);
        user.setName("짐승내");
        log.info("RESPONSE user: {}", user);
        log.info("RESPONSE userId: {}, userName: {}, test: {}", userId, userName, test);

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
