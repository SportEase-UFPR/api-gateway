package br.ufpr.sportease.apigateway.controller.cadastros;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.model.dto.usuario.*;
import br.ufpr.sportease.apigateway.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/usuarios")
@CrossOrigin(origins ="*")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final MsCadastrosClient msCadastrosClient;

    public UsuarioController(UsuarioService usuarioService, MsCadastrosClient msCadastrosClient) {
        this.usuarioService = usuarioService;
        this.msCadastrosClient = msCadastrosClient;
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> excluirUsuarioPorId(@PathVariable Long idUsuario, @RequestHeader("AuthorizationApi") String tokenApi) {
        usuarioService.excluirUsuarioPorId(idUsuario, tokenApi);
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
}
