package br.ufpr.sportease.apigateway.model.dto.usuario;

import br.ufpr.sportease.apigateway.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StatusBloqueioContaResponse {
    private Long idUsuario;
    private Boolean bloqueada;

    public StatusBloqueioContaResponse(Usuario usuario) {
        this.idUsuario = usuario.getId();
        this.bloqueada = usuario.getBloqueada();
    }
}
