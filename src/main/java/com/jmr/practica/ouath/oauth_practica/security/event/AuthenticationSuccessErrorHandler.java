package com.jmr.practica.ouath.oauth_practica.security.event;

import com.jmr.practica.entities_practica.libreria_custom_users_practica.models.User;
import com.jmr.practica.ouath.oauth_practica.services.IUserService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	@Autowired
	private IUserService userService;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {

		log.info("Success Login: " + authentication.getName());
		
		User user = userService.findByUsername(authentication.getName());
		
		if(user.getAttempts() != null && user.getAttempts() > 0) {
			user.setAttempts(0);
			userService.update(user, user.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {

		String mensaje = "Error en el Login: " + exception.getMessage();

		log.error(mensaje);

		try
		{
			User user = userService.findByUsername(authentication.getName());

			if (user.getAttempts() == null) {
				user.setAttempts(0);
			}

			int attempts = user.getAttempts();
			
			log.info("Intentos actual es de: " + attempts);
			
			user.setAttempts(attempts+1);
			
			log.info("Intentos después es de: " + attempts);
			
			if(user.getAttempts() >= 3) {
				String errorMaxIntentos = String.format("El user %s des-habilitado por máximos intentos.", user.getUsername());
				log.error(errorMaxIntentos);
				user.setEnabled(false);
			}
			
			userService.update(user, user.getId());
			
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}

	}

}
