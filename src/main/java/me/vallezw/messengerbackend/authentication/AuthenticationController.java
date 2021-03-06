package me.vallezw.messengerbackend.authentication;


import me.vallezw.messengerbackend.authentication.database.User;
import me.vallezw.messengerbackend.authentication.database.UserRepository;
import me.vallezw.messengerbackend.authentication.models.AuthenticationRequest;
import me.vallezw.messengerbackend.authentication.models.AuthenticationResponse;
import me.vallezw.messengerbackend.authentication.services.MyUserDetailsService;
import me.vallezw.messengerbackend.authentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }
        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String jwt = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        catch (NullPointerException e) {
            throw new Exception("Incorrect username or password", e);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user){
        if (userRepository.existsById(user.getUsername())) {
           return new ResponseEntity<>("Username already in use", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email already in use", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        try {
            ResponseEntity<?> res = createAuthenticationToken(new AuthenticationRequest(user.getUsername(), user.getPassword()));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
