package br.ufpr.sportease.apigateway.repository;

import br.ufpr.sportease.apigateway.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByLogin(String login);
    UserDetails findByLogin(String login);
    Optional<Usuario> getUsuarioByLogin(String login);
}
