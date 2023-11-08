package br.ufpr.sportease.apigateway.controller.comunicacoes;

import br.ufpr.sportease.apigateway.client.MsComunicacoesClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/notificacoes")
public class NotificacoesController {

    private final MsComunicacoesClient msComunicacoesClient;

    public NotificacoesController(MsComunicacoesClient msComunicacoesClient) {
        this.msComunicacoesClient = msComunicacoesClient;
    }

    @GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Object>> buscarNotificacoesCliente(@RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msComunicacoesClient.buscarNotificacoesCliente(token));
    }

    @PutMapping("/marcar-como-lida")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> marcarNotificacoesComoLida(@RequestHeader("AuthorizationUser") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msComunicacoesClient.marcarNotificacoesComoLida(token));

    }
}
