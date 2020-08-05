package me.vallezw.messengerbackend;

import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/hello")
    public String hello(@RequestHeader (name="Authorization") String header) {
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        System.out.println("Gu");
        return "Hello " + username;
    }
}
