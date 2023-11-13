package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.model.dto.usuario.*;
import br.ufpr.sportease.apigateway.security.TokenService;
import br.ufpr.sportease.apigateway.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/usuarios")
@CrossOrigin(origins ="*")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final MsCadastrosClient msCadastrosClient;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, MsCadastrosClient msCadastrosClient, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.msCadastrosClient = msCadastrosClient;
        this.tokenService = tokenService;
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> excluirUsuarioPorId(@PathVariable Long idUsuario, @RequestHeader("AuthorizationApi") String tokenApi) {
        tokenService.validarTokenMs(tokenApi);
        usuarioService.excluirUsuarioPorId(idUsuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/ativacao/{tokenAtivacaoConta}")
    public ResponseEntity<AtivarContaResponse> ativarConta(@PathVariable String tokenAtivacaoConta) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.ativarConta(tokenAtivacaoConta));
    }

    @PostMapping("/email-recuperacao-senha")
    public ResponseEntity<Object> enviarEmailRecuperacaoSenha(@RequestBody Object request) {
        return ResponseEntity.status(HttpStatus.OK).body(msCadastrosClient.enviarEmailRecuperacaoSenha(request));
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<AlterarSenhaResponse> alterarSenha(@RequestBody @Valid AlterarSenhaRequest alterarSenhaRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.alterarSenha(alterarSenhaRequest));
    }

    @PutMapping("/bloquear-conta/{idUsuario}")
    public ResponseEntity<Void> bloquearConta(@PathVariable Long idUsuario, @RequestBody @Valid BloquearContaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.bloquearConta(idUsuario, request));
    }

    @PutMapping("/desbloquear-conta/{idUsuario}")
    public ResponseEntity<Void> desbloquearConta(@PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.desbloquearConta(idUsuario));
    }

    @GetMapping("/buscar-status-bloqueio-contas")
    public ResponseEntity<List<StatusBloqueioContaResponse>> buscarStatusBloqueioContas(@RequestHeader("AuthorizationApi") String token) {
        tokenService.validarTokenMs(token);

        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarStatusBloqueioContas());
    }
}
