package br.ufpr.sportease.apigateway.service;

import br.ufpr.sportease.apigateway.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public LoginService(UsuarioRepository repository) {
        this.usuarioRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return usuarioRepository.findByLogin(username);
    }
}
