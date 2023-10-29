package br.ufpr.sportease.apigateway.model.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClienteAlterarEmailRequest {
    @NotBlank(message = "O token é obrigatório")
    private String tokenUsuarioComNovoEmail;
}
