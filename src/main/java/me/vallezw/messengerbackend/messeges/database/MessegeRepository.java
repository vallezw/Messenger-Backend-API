package me.vallezw.messengerbackend.messeges.database;

import me.vallezw.messengerbackend.messeges.database.Messege;
import org.springframework.data.repository.CrudRepository;

public interface MessegeRepository extends CrudRepository<Messege, Long> {
}
