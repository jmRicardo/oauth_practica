package com.jmr.practica.ouath.oauth_practica.services;

import com.jmr.practica.entities_practica.libreria_custom_users_practica.models.User;
import com.jmr.practica.ouath.oauth_practica.feign.UserFeignClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    UserFeignClient client;

    public UserService(UserFeignClient userFeignClient) {
        this.client = userFeignClient;
    }

    @Override
    public User findByUsername(String username) {
        return client.findByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        return client.update(user,id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User user = client.findByUsername(username);
            
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

            log.info(String.format("Usuario autenticado: %s", username));

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true,
                    authorities);
        } catch (FeignException e) {
            String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            log.error(error);
            throw new UsernameNotFoundException(error);
        }
    }
}
