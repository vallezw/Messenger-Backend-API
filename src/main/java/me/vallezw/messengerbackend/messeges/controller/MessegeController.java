package me.vallezw.messengerbackend.messeges.controller;

import me.vallezw.messengerbackend.authentication.util.AuthenticationUtil;
import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import me.vallezw.messengerbackend.chats.database.Chat;
import me.vallezw.messengerbackend.chats.database.ChatRepository;
import me.vallezw.messengerbackend.messeges.database.Messege;
import me.vallezw.messengerbackend.messeges.database.MessegeRepository;
import me.vallezw.messengerbackend.messeges.models.MessegeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MessegeController {

    final
    AuthenticationUtil authenticationUtil;

    final
    JwtUtil jwtUtil;

    final
    ChatRepository chatRepository;

    final
    MessegeRepository messegeRepository;

    public MessegeController(AuthenticationUtil authenticationUtil, JwtUtil jwtUtil, ChatRepository chatRepository, MessegeRepository messegeRepository) {
        this.authenticationUtil = authenticationUtil;
        this.jwtUtil = jwtUtil;
        this.chatRepository = chatRepository;
        this.messegeRepository = messegeRepository;
    }


    @RequestMapping(value = "/chat/{id}", method = RequestMethod.POST) // Message senden
    public ResponseEntity<?> textChat(@PathVariable long id, @RequestHeader(name="Authorization") String header, @RequestBody MessegeRequest bodyMessege){
        if(!checkChats(id, header)){
            return new ResponseEntity<>("Chat not found", HttpStatus.NOT_FOUND);
        }
        Messege messege = new Messege(id, bodyMessege.getContent1(), bodyMessege.getContent2(), username);
        messegeRepository.save(messege);
        return new ResponseEntity<>("Created message successfully", HttpStatus.OK);
    }

    @RequestMapping("/chat/{id}/messages")
    public ResponseEntity<?> getAllMessagesAtChat(@PathVariable long id, @RequestHeader(name="Authorization") String header){
        if(!checkChats(id, header)){
            return new ResponseEntity<>("Chat not found", HttpStatus.NOT_FOUND);
        }

        List<Messege> messages = messegeRepository.getAllByChatId(id);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    private boolean checkChats(long id, String header){
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<Chat> chat = chatRepository.findById(id);

        if(!authenticationUtil.checkChat(username, chat)){
            return false;
        }
        if(!authenticationUtil.checkAuthentication(username, chat)) {
            return false;
        }
        return true;
    }
}
