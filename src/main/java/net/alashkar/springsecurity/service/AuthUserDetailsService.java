package net.alashkar.springsecurity.service;

import lombok.AllArgsConstructor;
import net.alashkar.springsecurity.entity.AuthUser;
import net.alashkar.springsecurity.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AuthUser> authUser = authUserRepository.findByUsername(username.toLowerCase());

        if (!authUser.isPresent()){
            throw new UsernameNotFoundException("Username not found: " + username);
        }else {
            return User.builder()
                    .username(authUser.get().getUsername())
                    .password(authUser.get().getPassword())
                    .disabled(!authUser.get().isActive())
                    .build();
        }
    }
}
