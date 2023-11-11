package br.ufpr.sportease.apigateway.model.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloquearContaRequest {
    @NotBlank(message = "O campo justificativa é obrigatório")
    private String justificativa;
}
