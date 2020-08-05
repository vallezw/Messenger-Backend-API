package me.vallezw.messengerbackend.database.chats;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChatRepository extends CrudRepository<Chat, Long> {
    public List<Chat> findByUser1OrUser2(String user1, String user2);

}
