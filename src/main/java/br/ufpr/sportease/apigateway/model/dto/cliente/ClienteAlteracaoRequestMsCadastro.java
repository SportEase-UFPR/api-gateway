package br.ufpr.sportease.apigateway.model.dto.cliente;

import lombok.*;

@Getter
@Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteAlteracaoRequestMsCadastro {
    private String nome;
    private String email;

    public ClienteAlteracaoRequestMsCadastro(ClienteAlteracaoRequest request) {
        this.nome = request.getNome();
        this.email = request.getEmail();
    }
}
