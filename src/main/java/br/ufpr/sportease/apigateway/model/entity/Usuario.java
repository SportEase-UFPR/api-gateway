package br.ufpr.sportease.apigateway.model.entity;

import br.ufpr.sportease.apigateway.model.dto.cliente.ClienteCriacaoRequest;
import br.ufpr.sportease.apigateway.model.enums.NivelAcesso;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity(name = "tb_usuarios")
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String login;

    @Column(nullable = false, length = 100)
    private String senha;

    @Enumerated(EnumType.STRING)
    private NivelAcesso nivelAcesso;

    @Column(nullable = false)
    private Boolean ativa;

    @Column(nullable = false)
    private Boolean bloqueada;

    private String motivoBloqueio;

    public Usuario(String login, String senha, NivelAcesso nivelAcesso) {
        this.login = login;
        this.senha = new BCryptPasswordEncoder().encode(senha);
        this.nivelAcesso = nivelAcesso;
        this.ativa = false;
        this.bloqueada = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(nivelAcesso.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
