package com.hoaxify.ws.configuration;

import com.hoaxify.ws.entity.User;
import com.hoaxify.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User inDb = userRepository.findByUsername(username);
        if(inDb == null){
            throw new UsernameNotFoundException("User not found");
        }
        return inDb;
    }
}
