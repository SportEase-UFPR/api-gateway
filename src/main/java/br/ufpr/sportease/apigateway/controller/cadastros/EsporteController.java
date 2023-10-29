package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/esportes")
@CrossOrigin(origins ="*")
@RestController
public class EsporteController {

    private final MsCadastrosClient msCadastrosClient;

    public EsporteController(MsCadastrosClient msCadastrosClient) {
        this.msCadastrosClient = msCadastrosClient;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> criarEsporte(@RequestBody Object request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(msCadastrosClient.criarEsporte(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> listarEsportes() {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.listarEsportes());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> excluirEsporte(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.excluirEsporte(id));
    }
}
