package com.jmr.practica.ouath.oauth_practica.services;

import com.jmr.practica.entities_practica.libreria_custom_users_practica.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements IUserService, UserDetailsService {
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public User update(User user, Long id) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
