package me.vallezw.messengerbackend.chats.controller;

import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import me.vallezw.messengerbackend.chats.database.Chat;
import me.vallezw.messengerbackend.chats.database.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    JwtUtil jwtUtil;


    @RequestMapping("/getchats")
    public List<Chat> getChats(@RequestHeader (name="Authorization") String header) {
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        List<Chat> chats = chatRepository.findByUser1OrUser2(username, username);
        return chats;
    }

    @RequestMapping(value = "/createchat", method = RequestMethod.POST)
    public ResponseEntity<?> createChat(@Reque){

    }
}
