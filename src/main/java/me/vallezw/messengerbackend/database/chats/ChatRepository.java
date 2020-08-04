package me.vallezw.messengerbackend.database.chats;

import org.springframework.data.repository.CrudRepository;


public interface ChatRepository extends CrudRepository<Chat, Long> {
}
