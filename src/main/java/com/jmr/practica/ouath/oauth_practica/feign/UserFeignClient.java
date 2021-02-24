package com.jmr.practica.ouath.oauth_practica.feign;

import com.jmr.practica.entities_practica.libreria_custom_users_practica.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "users-practica")
public interface UserFeignClient {

    @GetMapping("/users/search/by-username")
    User findByUsername(@RequestParam String username);

    @PutMapping("/users/{id}")
    User update(@RequestBody User user, @PathVariable Long id);
}
