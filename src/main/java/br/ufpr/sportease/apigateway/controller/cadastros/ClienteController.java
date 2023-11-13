package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.model.dto.cliente.ClienteAlteracaoRequest;
import br.ufpr.sportease.apigateway.model.dto.cliente.ClienteAlterarEmailRequest;
import br.ufpr.sportease.apigateway.model.dto.cliente.ClienteCriacaoRequest;
import br.ufpr.sportease.apigateway.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
@RestController
public class ClienteController {

    private final MsCadastrosClient msCadastrosClient;
    private final ClienteService clienteService;

    public ClienteController(MsCadastrosClient msCadastrosClient, ClienteService clienteService) {
        this.msCadastrosClient = msCadastrosClient;
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Object> criarCliente(@Valid @RequestBody ClienteCriacaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.criarCliente(request));
    }

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> buscarClientePorId(@PathVariable Long clienteId) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarClientePorId(clienteId));
    }

    @GetMapping("/cliente-logado")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> buscarDadosClienteLogado(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarDadosClienteLogado(token));
    }

    @PutMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> atualizarDadosCliente(@RequestBody @Valid ClienteAlteracaoRequest request, @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarDadosCliente(token, request));
    }

    @PutMapping("/alterar-email")
    public ResponseEntity<Object> alterarEmail(@Valid @RequestBody ClienteAlterarEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.alterarEmailCliente(request));
    }

    @GetMapping("/buscar-id-por-id-usuario/{idUsuario}")
    public ResponseEntity<Object> buscarIdPorIdUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarIdClientePorIdUsuario(idUsuario));
    }

    @GetMapping("/buscar-emails-clientes")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<List<Object>> buscarEmailsClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarEmailsClientes());
    }

    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<List<Object>> buscarClienteDetalhadoPorId() {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarClienteDetalhadoPorId());
    }
}
