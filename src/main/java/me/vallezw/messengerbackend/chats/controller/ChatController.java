package me.vallezw.messengerbackend.chats.controller;

import me.vallezw.messengerbackend.authentication.services.MyUserDetailsService;
import me.vallezw.messengerbackend.authentication.util.AuthenticationUtil;
import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import me.vallezw.messengerbackend.chats.database.Chat;
import me.vallezw.messengerbackend.chats.database.ChatRepository;
import me.vallezw.messengerbackend.chats.models.CreateChatRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChatController {

    final
    MyUserDetailsService userDetailsService;

    final
    ChatRepository chatRepository;

    final
    JwtUtil jwtUtil;

    final
    AuthenticationUtil authenticationUtil;

    public ChatController(ChatRepository chatRepository, JwtUtil jwtUtil, MyUserDetailsService userDetailsService, AuthenticationUtil authenticationUtil) {
        this.chatRepository = chatRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationUtil = authenticationUtil;
    }

    @RequestMapping(value = "/getchats", method = RequestMethod.GET)
    public List<Chat> getChats(@RequestHeader (name="Authorization") String header) {
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        return chatRepository.findAllByUser1OrUser2(username, username);
    }

    @RequestMapping("/chat/{id}")
    public ResponseEntity<?> getChat(@PathVariable long id, @RequestHeader (name="Authorization") String header){
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<Chat> chat = chatRepository.findById(id);
        if(!authenticationUtil.checkChat(username, chat)){
            return new ResponseEntity<>("Chat not found", HttpStatus.NOT_FOUND);
        }
        if(!authenticationUtil.checkAuthentication(username, chat)){
            return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Optional<Chat>>(chat , HttpStatus.OK);
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
