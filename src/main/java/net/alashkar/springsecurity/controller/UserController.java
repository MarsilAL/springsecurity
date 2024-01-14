package net.alashkar.springsecurity.controller;

import lombok.AllArgsConstructor;
import net.alashkar.springsecurity.entity.AuthUser;
import net.alashkar.springsecurity.repository.AuthUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class UserController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody AuthUser user){
        try {
            if (authUserRepository.findByUsername(user.getUsername()).isPresent())
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already ");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            AuthUser savedUser = authUserRepository.save(user);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }
}
