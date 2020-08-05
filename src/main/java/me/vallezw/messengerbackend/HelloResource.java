package me.vallezw.messengerbackend;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @RequestMapping("/hello")
    public String hello(@RequestHeader (name="Authorization") String header) {
        String token = header.substring(7);
        return token;
    }
}
