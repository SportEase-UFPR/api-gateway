package br.ufpr.sportease.apigateway.model.dto.adm;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdmAlterarEmailRequest {
    @NotBlank(message = "O token é obrigatório")
    private String tokenUsuarioComNovoEmail;
}
