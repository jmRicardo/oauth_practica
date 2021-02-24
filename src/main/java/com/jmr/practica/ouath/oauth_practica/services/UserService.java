package com.jmr.practica.ouath.oauth_practica.services;

import com.jmr.practica.entities_practica.libreria_custom_users_practica.models.User;
import com.jmr.practica.ouath.oauth_practica.feign.UserFeignClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public User findByUsername(String username) {
        return userFeignClient.findByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        return userFeignClient.update(user,id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = userFeignClient.findByUsername(username);
            return null;
        } catch (FeignException e) {
            String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            log.error(error);
            throw new UsernameNotFoundException(error);
        }
    }
}
