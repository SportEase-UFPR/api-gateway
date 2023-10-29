package br.ufpr.sportease.apigateway.model.dto.adm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmCriacaoRequestMsCadastro {
    private String nome;
    private String email;
    private String cpf;
    private Long idUsuario;

    public AdmCriacaoRequestMsCadastro(AdmCriacaoRequest request) {
        this.nome = request.getNome();
        this.email = request.getEmail();
        this.cpf = request.getCpf();
    }
}
