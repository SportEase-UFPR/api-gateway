package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/espacos-esportivos")
@CrossOrigin(origins ="*")
@RestController
public class EspacoEsportivoController {

    private final MsCadastrosClient msCadastrosClient;

    public EspacoEsportivoController(MsCadastrosClient msCadastrosClient) {
        this.msCadastrosClient = msCadastrosClient;
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
    @PreAuthorize("hasRole('ADM')")
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
}
