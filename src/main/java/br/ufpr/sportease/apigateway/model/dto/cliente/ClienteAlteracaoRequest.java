package br.ufpr.sportease.apigateway.model.dto.cliente;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteAlteracaoRequest {
    private String nome;
    @Email(message = "O email deve ser válido")
    private String email;
    private String senha;
    private Boolean alunoUFPR;
    private String grr;
}
