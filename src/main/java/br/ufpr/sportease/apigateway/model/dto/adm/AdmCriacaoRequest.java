package br.ufpr.sportease.apigateway.model.dto.adm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmCriacaoRequest {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "O email deve ser válido")
    private String email;

    @CPF(message = "O cpf deve ser válido")
    @NotBlank(message = "O cpf é obrigatório")
    private String cpf;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}
