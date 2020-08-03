package me.vallezw.messengerbackend.authentication.services;

import me.vallezw.messengerbackend.authentication.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<me.vallezw.messengerbackend.authentication.database.User> userOptional = userRepository.findById(userName);
        me.vallezw.messengerbackend.authentication.database.User user
                = userOptional.orElse(null);
        if (user == null) {
            throw new NullPointerException();
        }

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
