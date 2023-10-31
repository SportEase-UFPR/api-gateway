package br.ufpr.sportease.apigateway.controller.locacoes;


import br.ufpr.sportease.apigateway.client.MsLocacoesClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    private final MsLocacoesClient msLocacoesClient;

    public LocacaoController(MsLocacoesClient msLocacoesClient) {
        this.msLocacoesClient = msLocacoesClient;
    }

    @PostMapping("/solicitar-locacao")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADM')")
    public ResponseEntity<Object> solicitarLocacao(
            @RequestBody Object request,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.CREATED).body(msLocacoesClient.solicitarLocacao(request, token));
    }

    @PostMapping("/horarios-disponiveis")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADM')")
    public ResponseEntity<Object> verificarHorariosDisponiveisParaLocacao(
            @RequestBody Object horarioDisponivelRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(msLocacoesClient.verificarHorariosDisponiveisParaLocacao(horarioDisponivelRequest));
    }

    @GetMapping("/listar-reservas-em-andamento")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Object>> listarReservasEmAndamento(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msLocacoesClient.listarReservasEmAndamento(token));
    }

    @GetMapping("/listar-historico-reservas")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<Object>> listarHistoricoReservas(
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msLocacoesClient.listarHistoricoReservas(token));
    }

    @PutMapping("/cancelar-reserva/{idReserva}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> cancelarReserva(
            @PathVariable Long idReserva,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msLocacoesClient.cancelarReserva(token, idReserva));
    }

    @PutMapping("/confirmar-uso/{idReserva}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Object> confirmarUsoReserva(
            @PathVariable Long idReserva,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msLocacoesClient.confirmarUsoReserva(token, idReserva));
    }
}
