package br.ufpr.sportease.apigateway.client;

import br.ufpr.sportease.apigateway.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MsCadastrosClient {

    @Value("${url.ms.cadastros.adm}")
    private String urlMsCadastrosAdm;

    @Value("${url.ms.cadastros.clientes}")
    private String urlMsCadastrosClientes;

    @Value("${url.ms.cadastros.espacos_esportivos}")
    private String urlMsCadastrosEE;

    @Value("${url.ms.cadastros.esportes}")
    private String urlMsCadastrosEsportes;

    @Value("${url.ms.cadastros.usuarios}")
    private String urlMsCadastrosUsuarios;

    public static final String AUTHORIZATION_USER = "AuthorizationUser";

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public MsCadastrosClient(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", tokenService.gerarTokenApiGateway());
        return headers;
    }

    public Object alterarEmailSolicitacao(Object request, String token) {
        String url = urlMsCadastrosAdm + "/alterar-email-solicitacao";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object alterarEmailAdm(Object request) {
        String url = urlMsCadastrosAdm + "/alterar-email";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object alterarNome(Object request, String token) {
        String url = urlMsCadastrosAdm + "/alterar-nome";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url,HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();

    }

    public Object criarAdm(Object request) {
        String url = urlMsCadastrosAdm;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public List<Object> listarAdms() {
        String url = urlMsCadastrosAdm;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();

    }

    public Object buscarAdmPorId(Long idAdm) {
        String url = urlMsCadastrosAdm + "/" + idAdm;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object buscarAdmPorIdUsuario(String token) {
        String url = urlMsCadastrosAdm + "/adm-logado";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();

    }

    public Object excluirAdmPorId(Long idAdm) {
        String url = urlMsCadastrosAdm + "/" + idAdm;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object editarDadosAdm(String token, Object request) {
        String url = urlMsCadastrosAdm;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object buscarIdAdmPorIdUsuario(Long idUsuario) {
        String url = urlMsCadastrosAdm + "/buscar-id-por-id-usuario/" + idUsuario;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object criarCliente(Object request) {
        String url = urlMsCadastrosClientes;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object buscarClientePorId(Long clienteId) {
        String url = urlMsCadastrosClientes + "/" + clienteId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object buscarDadosClienteLogado(String token) {
        String url = urlMsCadastrosClientes + "/cliente-logado";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object atualizarDadosCliente(String token, Object request) {
        String url = urlMsCadastrosClientes;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object alterarEmailCliente(Object request) {
        String url = urlMsCadastrosClientes + "/alterar-email";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object buscarIdClientePorIdUsuario(Long idUsuario) {
        String url = urlMsCadastrosClientes + "/buscar-id-por-id-usuario/" + idUsuario;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object criarEspacoEsportivo(Object request) {
        String url = urlMsCadastrosEE;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object listarEspacosEsportivos() {
        String url = urlMsCadastrosEE;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object listarEspacosEsportivosDisponiveis() {
        String url = urlMsCadastrosEE + "/disponiveis";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object buscarEspacoEsportivoPorId(Long espEsportivoId) {
        String url = urlMsCadastrosEE + "/" + espEsportivoId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object excluirEspacoEsportivoPorId(Long espEsportivoId) {
        String url = urlMsCadastrosEE + "/" + espEsportivoId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object editarEspacoEsportivo(Long espEsportivoId, Object request) {
        String url = urlMsCadastrosEE + "/" + espEsportivoId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object criarEsporte(Object request) {
        String url = urlMsCadastrosEsportes;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object listarEsportes() {
        String url = urlMsCadastrosEsportes;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object excluirEsporte(Long id) {
        String url = urlMsCadastrosEsportes + "/" + id;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object enviarEmailRecuperacaoSenha(Object request) {
        String url = urlMsCadastrosUsuarios + "/email-recuperacao-senha";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public List<Object> buscarEmailsClientes() {
        String url = urlMsCadastrosClientes + "/emails-clientes";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();

    }

    public Object enviarEmailClientes(Object request) {
        String url = urlMsCadastrosClientes + "/enviar-email";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object enviarEmailTodosClientes(Object request) {
        String url = urlMsCadastrosClientes + "/enviar-email-todos";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }
}
