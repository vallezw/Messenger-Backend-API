package me.vallezw.messengerbackend.chats.controller;

import me.vallezw.messengerbackend.authentication.services.MyUserDetailsService;
import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import me.vallezw.messengerbackend.chats.database.Chat;
import me.vallezw.messengerbackend.chats.database.ChatRepository;
import me.vallezw.messengerbackend.chats.models.CreateChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    final
    MyUserDetailsService userDetailsService;

    final
    ChatRepository chatRepository;

    final
    JwtUtil jwtUtil;

    public ChatController(ChatRepository chatRepository, JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
        this.chatRepository = chatRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/getchats", method = RequestMethod.GET)
    public List<Chat> getChats(@RequestHeader (name="Authorization") String header) {
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        return chatRepository.findAllByUser1OrUser2(username, username);
    }

    @RequestMapping(value = "/createchat", method = RequestMethod.POST)
    public ResponseEntity<?> createChat(@RequestBody CreateChatRequest body, @RequestHeader (name="Authorization") String header){
        String token = header.substring(7);
        String user1 = jwtUtil.extractUsername(token);
        String user2 = body.getUser();

        // Check if user exists
        try {
            UserDetails userdetails = userDetailsService.loadUserByUsername(user2);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // Check if the chat already exists
        if(chatRepository.existsByUser1AndUser2(user1, user2) || chatRepository.existsByUser1AndUser2(user2, user1)){
            return new ResponseEntity<>("This chat already exists", HttpStatus.BAD_REQUEST);
        }

        Chat chat = new Chat(user1, user2);
        chatRepository.save(chat);
        return new ResponseEntity<>("Created Chat", HttpStatus.OK);
    }
}
