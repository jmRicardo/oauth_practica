package com.jmr.practica.ouath.oauth_practica.services;

import com.jmr.practica.entities_practica.libreria_custom_users_practica.models.User;
import org.springframework.web.bind.annotation.*;

public interface IUserService {

    User findByUsername(String username);

    User update(User user,Long id);
}
