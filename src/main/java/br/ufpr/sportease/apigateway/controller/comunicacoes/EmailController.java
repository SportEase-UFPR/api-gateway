package br.ufpr.sportease.apigateway.controller.comunicacoes;

import br.ufpr.sportease.apigateway.client.MsComunicacoesClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/email")
public class EmailController {

    private final MsComunicacoesClient msComunicacoesClient;

    public EmailController(MsComunicacoesClient msComunicacoesClient) {
        this.msComunicacoesClient = msComunicacoesClient;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> enviarEmailClientes(@RequestBody Object request) {
        return ResponseEntity.status(HttpStatus.OK).body(msComunicacoesClient.enviarEmailClientes(request));
    }

    @PostMapping("/todos")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> enviarEmailTodosClientes(@RequestBody Object request) {
        return ResponseEntity.status(HttpStatus.OK).body(msComunicacoesClient.enviarEmailTodosClientes(request));
    }
}
