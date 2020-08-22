package me.vallezw.messengerbackend.notifications.controller;

import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import me.vallezw.messengerbackend.chats.database.ChatRepository;
import me.vallezw.messengerbackend.notifications.database.Notification;
import me.vallezw.messengerbackend.notifications.database.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class NotificationController {

    final private NotificationRepository notificationRepository;3

    final private JwtUtil jwtUtil;


    public NotificationController(NotificationRepository notificationRepository, JwtUtil jwtUtil) {
        this.notificationRepository = notificationRepository;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/deleteNotification/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteChat(@PathVariable long id, @RequestHeader(name="Authorization") String header) {
        String token = header.substring(7);
        String username = jwtUtil.extractUsername(token);
        Optional<Notification> n = notificationRepository.findById(id);
        if(n.isEmpty() || !n.get().getReceiver().equals(username)){
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        notificationRepository.deleteById(id);
        return new ResponseEntity<>("Deleted Notification " + id + "successfully", HttpStatus.OK);
    }
}
