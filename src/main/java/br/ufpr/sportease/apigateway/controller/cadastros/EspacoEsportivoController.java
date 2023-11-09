package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.client.MsLocacoesClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/espacos-esportivos")
@CrossOrigin(origins ="*")
@RestController
public class EspacoEsportivoController {

    private final MsCadastrosClient msCadastrosClient;
    private final MsLocacoesClient msLocacoesClient;

    public EspacoEsportivoController(MsCadastrosClient msCadastrosClient, MsLocacoesClient msLocacoesClient) {
        this.msCadastrosClient = msCadastrosClient;
        this.msLocacoesClient = msLocacoesClient;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> criarEspacoEsportivo(@RequestBody Object request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(msCadastrosClient.criarEspacoEsportivo(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADM') or hasRole('CLIENTE')")
    public ResponseEntity<Object> listarEspacosEsportivos() {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.listarEspacosEsportivos());
    }

    @GetMapping("/disponiveis")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADM')")
    public ResponseEntity<Object> listarEspacosEsportivosDisponiveis() {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.listarEspacosEsportivosDisponiveis());
    }

    @GetMapping("/{espEsportivoId}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ADM')")
    public ResponseEntity<Object> buscarEspacoEsportivoPorId(@PathVariable Long espEsportivoId) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarEspacoEsportivoPorId(espEsportivoId));
    }

    @DeleteMapping("/{espEsportivoId}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> excluirEspacoEsportivoPorId(@PathVariable Long espEsportivoId) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.excluirEspacoEsportivoPorId(espEsportivoId));
    }

    @PutMapping("/{espEsportivoId}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> editarDadosEspacoEsportivo(@RequestBody Object request, @PathVariable Long espEsportivoId) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.editarEspacoEsportivo(espEsportivoId, request));
    }

    @GetMapping("/comentarios/{idEspacoEsportivo}")
    @PreAuthorize("hasRole('ADM') or hasRole('CLIENTE')")
    public ResponseEntity<List<Object>> listarComentariosPorEspacoEsportivo(@PathVariable Long idEspacoEsportivo) {
        return ResponseEntity.status(HttpStatus.OK).body(msLocacoesClient.listarComentariosPorEspacoEsportivo(idEspacoEsportivo));
    }
}
