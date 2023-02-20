package com.example.appecommerce.service;

import com.example.appecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Class used to implement UserDetailsService and override loadUserByUsername() method
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Method used to get Users by their username
     * @param username username of the User
     * @return UserDetails
     * @throws UsernameNotFoundException If user with this username does not exist, it returns UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("The username is not found"));
    }
}
