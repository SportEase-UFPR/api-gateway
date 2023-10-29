package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.model.dto.adm.AdmAlteracaoRequest;
import br.ufpr.sportease.apigateway.model.dto.adm.AdmAlterarEmailRequest;
import br.ufpr.sportease.apigateway.model.dto.adm.AdmCriacaoRequest;
import br.ufpr.sportease.apigateway.service.AdmService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/adm")
@CrossOrigin(origins ="*")
@RestController
public class AdmController {

    private final MsCadastrosClient msCadastrosClient;
    private final AdmService admService;

    public AdmController(MsCadastrosClient msCadastrosClient, AdmService admService) {
        this.msCadastrosClient = msCadastrosClient;
        this.admService = admService;
    }


    @PostMapping("/alterar-email-solicitacao")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> alterarEmailSolicitacao(@RequestBody Object request,
                                                          @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.alterarEmailSolicitacao(request, token));
    }

    @PutMapping("/alterar-email")
    public ResponseEntity<Object> alterarEmail(@Valid @RequestBody AdmAlterarEmailRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.alterarEmailAdm(request));
    }

    @PutMapping("/alterar-nome")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> alterarNome
            (@RequestBody Object request,
             @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.alterarNome(request, token));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> criarAdm(@Valid @RequestBody AdmCriacaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(admService.criarAdm(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<List<Object>> listarAdms() {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.listarAdms());
    }

    @GetMapping("/{admId}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> buscarAdmPorId(@PathVariable Long admId) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarAdmPorId(admId));
    }

    @GetMapping("/adm-logado")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> buscarAdmPorIdUsuario(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarAdmPorIdUsuario(token));
    }

    @DeleteMapping("/{admId}")
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> excluirAdmPorId(@PathVariable Long admId) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.excluirAdmPorId(admId));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity<Object> editarDadosAdm(@RequestBody @Valid AdmAlteracaoRequest request,
                                                 @RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(admService.editarDadosAdm(token, request));
    }

    @GetMapping("/buscar-id-por-id-usuario/{idUsuario}")
    public ResponseEntity<Object> buscarIdAdmPorIdUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.buscarIdAdmPorIdUsuario(idUsuario));
    }
}
