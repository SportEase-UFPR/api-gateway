package br.ufpr.sportease.apigateway.model.dto.adm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdmAlteracaoResponse {
    private String mensagem;
}
