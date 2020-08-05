package me.vallezw.messengerbackend.chats.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChatRepository extends CrudRepository<Chat, Long> {
    public List<Chat> findAllByUser1OrUser2(String user1, String user2);
    public boolean existsByUser1AndUser2(String user1, String user2);
}
