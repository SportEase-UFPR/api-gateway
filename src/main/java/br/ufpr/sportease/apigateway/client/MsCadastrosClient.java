package br.ufpr.sportease.apigateway.client;

import br.ufpr.sportease.apigateway.model.dto.cliente.ClienteBuscaResponse;
import br.ufpr.sportease.apigateway.security.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MsCadastrosClient {

    @Value("${url.ms.cadastros}")
    private String urlMsCadastros;

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
        String url = urlMsCadastros + "/adm/alterar-email-solicitacao";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object alterarEmailAdm(Object request) {
        String url = urlMsCadastros + "/adm/alterar-email";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object alterarNome(Object request, String token) {
        String url = urlMsCadastros + "/adm/alterar-nome";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url,HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();

    }

    public Object criarAdm(Object request) {
        String url = urlMsCadastros + "/adm";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public List<Object> listarAdms() {
        String url = urlMsCadastros + "/adm";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();

    }

    public Object buscarAdmPorId(Long idAdm) {
        String url = urlMsCadastros + "/adm/" + idAdm;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object buscarAdmPorIdUsuario(String token) {
        String url = urlMsCadastros + "/adm/adm-logado";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();

    }

    public Object excluirAdmPorId(Long idAdm) {
        String url = urlMsCadastros + "/adm/" + idAdm;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object editarDadosAdm(String token, Object request) {
        String url = urlMsCadastros + "/adm";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object buscarIdAdmPorIdUsuario(Long idUsuario) {
        String url = urlMsCadastros + "/adm/buscar-id-por-id-usuario/" + idUsuario;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object criarCliente(Object request) {
        String url = urlMsCadastros + "/clientes";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object buscarClientePorId(Long clienteId) {
        String url = urlMsCadastros + "/clientes/" + clienteId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public ClienteBuscaResponse buscarClientePorId2(Long clienteId) {
        String url = urlMsCadastros + "/clientes/" + clienteId;
        HttpHeaders headers = gerarCabecalho();
        var response =  restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
        return new ClienteBuscaResponse(response);
    }

    public Object buscarDadosClienteLogado(String token) {
        String url = urlMsCadastros + "/clientes/cliente-logado";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object atualizarDadosCliente(String token, Object request) {
        String url = urlMsCadastros + "/clientes";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object alterarEmailCliente(Object request) {
        String url = urlMsCadastros + "clientes/alterar-email";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object buscarIdClientePorIdUsuario(Long idUsuario) {
        String url = urlMsCadastros + "clientes/buscar-id-por-id-usuario/" + idUsuario;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }


    public List<Object> buscarEmailsClientes() {
        String url = urlMsCadastros + "/clientes/buscar-emails-clientes";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public List<Object> buscarClienteDetalhadoPorId() {
        String url = urlMsCadastros + "/clientes/detalhes";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public Object criarEspacoEsportivo(Object request) {
        String url = urlMsCadastros + "/espacos-esportivos";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object listarEspacosEsportivos() {
        String url = urlMsCadastros + "/espacos-esportivos";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object listarEspacosEsportivosDisponiveis() {
        String url = urlMsCadastros + "/espacos-esportivos/disponiveis";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object buscarEspacoEsportivoPorId(Long espEsportivoId) {
        String url = urlMsCadastros + "/espacos-esportivos/" + espEsportivoId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object excluirEspacoEsportivoPorId(Long espEsportivoId) {
        String url = urlMsCadastros + "/espacos-esportivos/" + espEsportivoId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object editarEspacoEsportivo(Long espEsportivoId, Object request) {
        String url = urlMsCadastros + "/espacos-esportivos/" + espEsportivoId;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object criarEsporte(Object request) {
        String url = urlMsCadastros + "/esportes";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object listarEsportes() {
        String url = urlMsCadastros + "/esportes";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object excluirEsporte(Long id) {
        String url = urlMsCadastros + "/esportes/" + id;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object enviarEmailRecuperacaoSenha(Object request) {
        String url = urlMsCadastros + "/usuarios/email-recuperacao-senha";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object enviarEmailClientes(Object request) {
        String url = urlMsCadastros + "/clientes/enviar-email";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object enviarEmailTodosClientes(Object request) {
        String url = urlMsCadastros + "/clientes/enviar-email-todos";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }


}
