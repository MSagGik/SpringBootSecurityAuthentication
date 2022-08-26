package ru.msaggik.spring.SpringBootSecurityAuthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.msaggik.spring.SpringBootSecurityAuthentication.security.AuthProviderImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // внедрение провайдера аутентификации
    private final AuthProviderImpl authProvider;
    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    // метод настройки аутентификации
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider); // провайдер аутентификации
    }
}
