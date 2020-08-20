package me.vallezw.messengerbackend.messeges.database;

import me.vallezw.messengerbackend.messeges.database.Messege;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessegeRepository extends CrudRepository<Messege, Long> {
    public List<Messege> getAllByChatId(long id);
}
