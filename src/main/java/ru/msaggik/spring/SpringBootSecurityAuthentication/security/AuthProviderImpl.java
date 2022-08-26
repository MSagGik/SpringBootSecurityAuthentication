package ru.msaggik.spring.SpringBootSecurityAuthentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.msaggik.spring.SpringBootSecurityAuthentication.services.PersonDetailsService;

import java.util.Collections;

// сравнение пароля и логина введённых с формы с паролем и логином которые находятся в таблице БД
@Component
public class AuthProviderImpl implements AuthenticationProvider {

    // внедрение сервиса
    private final PersonDetailsService personDetailsService;
    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // логика аутентификации
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // получение имени пользователя
        String username = authentication.getName();
        // поиск данного имени в таблице person (использование сервиса)
        UserDetails personDetails = personDetailsService.loadUserByUsername(username);
        // получение введённого с формы пароля
        String password = authentication.getCredentials().toString();
        // сравнение полученного пароля с паролем в userDetails
        if (!password.equals(personDetails.getPassword())) // если пароли не совпадают,
            // то выбрасывается исключение
            throw new BadCredentialsException("Incorrect password");
        // иначе возвращается (принципал, пароль, список прав пользователя(пока пустой список))
        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    // метод указания для какого объекта действует аутентификация
    @Override
    public boolean supports(Class<?> authentication) {
        return true; // для всех объектов работает
    }
}
