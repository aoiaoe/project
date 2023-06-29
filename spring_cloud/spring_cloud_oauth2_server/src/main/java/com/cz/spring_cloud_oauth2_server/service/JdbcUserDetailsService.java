package com.cz.spring_cloud_oauth2_server.service;

import com.cz.spring_cloud_oauth2_server.dao.UserRepository;
import com.cz.spring_cloud_oauth2_server.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

@Service
public class JdbcUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Users user = this.userRepository.findByUserName(name);
        if(user == null){
            return null;
        }
        return new User(user.getUserName(), user.getPassword(), new ArrayDeque<>());
    }

}
