package br.ufpr.sportease.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() //desabilita a proteção contra csrf (o token já é uma proteção pra isso)
                .cors() // Adicionar essa linha para habilitar o suporte a CORS
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //desabilita a autenticação stateful que utiliza sessões e página de login do spring security
                .and().authorizeHttpRequests()

                //liberar endpoints:
                .requestMatchers(HttpMethod.POST, "/login").permitAll()

                .requestMatchers(HttpMethod.POST, "/usuarios/email-recuperacao-senha").permitAll()
                .requestMatchers(HttpMethod.PUT, "/usuarios/ativacao/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/usuarios/alterar-senha").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/usuarios/*").permitAll() //o bloqueio desse endpoint ocorre no fluxo principal do código

                .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
                .requestMatchers(HttpMethod.PUT, "/clientes/alterar-email").permitAll()
                .requestMatchers(HttpMethod.GET, "/clientes/buscar-id-por-id-usuario/*").permitAll()

                .requestMatchers(HttpMethod.PUT, "/adm/alterar-email").permitAll()
                .requestMatchers(HttpMethod.GET, "/adm/buscar-id-por-id-usuario/*").permitAll()

                .anyRequest().authenticated() //Para todos os outros endpoints é necessário estar autenticado

                // Chama o filtro securityFilter antes do filtro do spring
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        //método que cria um objeto AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
