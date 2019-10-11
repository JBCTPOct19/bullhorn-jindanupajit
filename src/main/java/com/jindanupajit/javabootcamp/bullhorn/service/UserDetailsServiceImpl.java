package com.jindanupajit.javabootcamp.bullhorn.service;

import com.jindanupajit.javabootcamp.bullhorn.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            return userDAO.getSpringUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return null;
        }


    }
}
