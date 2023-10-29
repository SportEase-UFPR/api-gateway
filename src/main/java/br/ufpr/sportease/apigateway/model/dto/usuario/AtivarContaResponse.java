package br.ufpr.sportease.apigateway.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class AtivarContaResponse {
    private Long idUsuario;
    private String mensagem;
}
