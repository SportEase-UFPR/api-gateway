package br.ufpr.sportease.apigateway.model.dto.cliente;

import lombok.*;

@Getter
@Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteAlteracaoRequestMsCadastro {
    private String nome;
    private String email;
    private Boolean alunoUFPR;
    private String grr;

    public ClienteAlteracaoRequestMsCadastro(ClienteAlteracaoRequest request) {
        this.nome = request.getNome();
        this.email = request.getEmail();
        this.alunoUFPR = request.getAlunoUFPR();
        this.grr = request.getGrr();
    }
}
