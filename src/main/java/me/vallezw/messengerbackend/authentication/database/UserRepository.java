package me.vallezw.messengerbackend.authentication.database;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    public boolean existsByEmail(String email);
}
