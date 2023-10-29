package br.ufpr.sportease.apigateway.model.dto.adm;

import lombok.*;

@Getter
@Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdmAlteracaoRequestMsCadastro {
    private String nome;
    private String email;

    public AdmAlteracaoRequestMsCadastro(AdmAlteracaoRequest request) {
        this.nome = request.getNome();
        this.email = request.getEmail();
    }
}
