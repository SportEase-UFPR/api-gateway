package br.ufpr.sportease.apigateway.client;

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
public class MsLocacoesClient {

    @Value("${url.ms.locacoes}")
    private String urlMsLocacoes;

    public static final String AUTHORIZATION_USER = "AuthorizationUser";

    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    public MsLocacoesClient(RestTemplate restTemplate, TokenService tokenService) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
    }

    private HttpHeaders gerarCabecalho() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", tokenService.gerarTokenApiGateway());
        return headers;
    }

    public Object solicitarLocacao(Object request, String token) {
        String url = urlMsLocacoes + "/solicitar-locacao";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public Object verificarHorariosDisponiveisParaLocacao(Object request) {
        String url = urlMsLocacoes + "/horarios-disponiveis";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }


    public List<Object> listarReservasEmAndamento(String token) {
        String url = urlMsLocacoes + "/listar-reservas-em-andamento";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public Object cancelarReserva(String token, Long idReserva) {
        String url = urlMsLocacoes + "/cancelar-reserva/" + idReserva;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(headers), Object.class).getBody();

    }

    public Object confirmarUsoReserva(String token, Long idReserva) {
        String url = urlMsLocacoes + "/confirmar-uso/" + idReserva;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(headers), Object.class).getBody();
    }

    public List<Object> listarHistoricoReservas(String token) {
        String url = urlMsLocacoes + "/listar-historico-reservas";
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public List<Object> listarReservasSolicitadas() {
        String url = urlMsLocacoes + "/listar-reservas-solicitadas";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public Object aprovarReserva(Long idReserva, String token) {
        String url = urlMsLocacoes + "/aprovar-reserva/" + idReserva;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(headers), Object.class).getBody();
    }

    public Object negarReserva(Long idReserva, String token, Object request) {
        String url = urlMsLocacoes + "/negar-reserva/" + idReserva;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public List<Object> buscarRelatorioDeReservas() {
        String url = urlMsLocacoes + "/relatorio-reservas";
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public Object avaliarReserva(Long idReserva, String token, Object request) {
        String url = urlMsLocacoes + "/avaliar-reserva/" + idReserva;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(request, headers), Object.class).getBody();
    }

    public List<Object> listarComentariosPorEspacoEsportivo(Long idEspacoEsportivo) {
        String url = urlMsLocacoes + "/comentarios/" + idEspacoEsportivo;
        HttpHeaders headers = gerarCabecalho();
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Object>>() {}).getBody();
    }

    public Object encerrarReserva(Long idReserva, String token, Object request) {
        String url = urlMsLocacoes + "/encerrar-reserva/" + idReserva;
        HttpHeaders headers = gerarCabecalho();
        headers.set(AUTHORIZATION_USER, token);
        return restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request, headers), Object.class).getBody();
    }

}
