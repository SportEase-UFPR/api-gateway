package br.ufpr.sportease.apigateway.client;

import br.ufpr.sportease.apigateway.model.dto.email.CriacaoEmailRequest;
import br.ufpr.sportease.apigateway.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MsComunicacoesClient {

    @Value("${url.ms.comunicacoes}")
    private String urlMsComunicacoes;

    public static final String AUTHORIZATION_USER = "AuthorizationUser";

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public MsComunicacoesClient(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", tokenService.gerarTokenApiGateway());
        return headers;
    }

    public List<Object> buscarNotificacoesCliente(String token) {
        String url = urlMsComunicacoes + "/notificacoes";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public Object marcarNotificacoesComoLida(String token) {
        String url = urlMsComunicacoes + "/notificacoes/marcar-como-lida";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(headers), Object.class).getBody();
    }

    public void enviarEmail(CriacaoEmailRequest request) {
        String url = urlMsComunicacoes + "/email";
        HttpHeaders headers = gerarCabecalho();
        restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class);
    }

}
