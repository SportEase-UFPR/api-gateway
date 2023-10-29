package br.ufpr.sportease.apigateway.model.dto.cliente;

import lombok.*;

@Getter
@Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCriacaoRequestMsCadastro {
    private String nome;
    private String email;
    private String cpf;
    private Boolean alunoUFPR;
    private String grr;
    private Long idUsuario;

    public ClienteCriacaoRequestMsCadastro(ClienteCriacaoRequest request) {
        this.nome = request.getNome();
        this.email = request.getEmail();
        this.cpf = request.getCpf();
        this.alunoUFPR = request.getAlunoUFPR();
        this.grr = request.getGrr();
    }
}
