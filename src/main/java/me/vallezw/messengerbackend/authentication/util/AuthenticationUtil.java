package me.vallezw.messengerbackend.authentication.util;

import me.vallezw.messengerbackend.chats.database.Chat;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationUtil {

    public boolean checkChat(String username, Optional<Chat> chat){
        return chat.isPresent();
    }

    public boolean checkAuthentication(String username, Optional<Chat> chat){
        return chat.get().getUser1().equals(username) && chat.get().getUser2().equals(username);
    }
}
