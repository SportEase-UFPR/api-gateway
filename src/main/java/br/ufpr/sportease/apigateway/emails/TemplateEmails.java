package br.ufpr.sportease.apigateway.emails;

import br.ufpr.sportease.apigateway.model.dto.cliente.ClienteBuscaResponse;
import br.ufpr.sportease.apigateway.model.dto.email.CriacaoEmailRequest;


public class TemplateEmails {
    private TemplateEmails(){/*vazio..*/}



    public static CriacaoEmailRequest emailBloqueioConta(ClienteBuscaResponse cliente, String motivoBloqueio) {
        var assunto = "SportEase - Sua conta foi bloqueada";
        var mensagem = """
        <html><body>
            <p>Olá %s,</p>
            <p>Sua conta no SportEase foi bloqueada pelo administrador.</p>
            <p>O motivo do bloqueio foi: "%s"</p>
            <p>Em caso de dúvidas, você pode responder a esse email.</p>
            <p>Atenciosamente,</p>
            <p>A Equipe SportEase.</p>
        </body></html>
        """.formatted(cliente.getNome(), motivoBloqueio);


        return CriacaoEmailRequest.builder()
                .assunto(assunto)
                .mensagem(mensagem)
                .email(cliente.getEmail())
                .build();
    }

    public static CriacaoEmailRequest emailDesbloqueioConta(ClienteBuscaResponse cliente) {
        var assunto = "SportEase - Sua conta foi desbloqueada!";
        var mensagem = """
        <html><body>
            <p>Olá %s,</p>
            <p>Sua conta no SportEase foi desbloqueada pelo administrador.
            Dessa forma, você já pode voltar a utilizar os nossos serviços.</p>
            <p>Atenciosamente,</p>
            <p>A Equipe SportEase.</p>
        </body></html>
        """.formatted(cliente.getNome());


        return CriacaoEmailRequest.builder()
                .assunto(assunto)
                .mensagem(mensagem)
                .email(cliente.getEmail())
                .build();
    }
}
