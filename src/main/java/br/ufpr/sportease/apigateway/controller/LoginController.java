package br.ufpr.sportease.apigateway.controller;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.exceptions.BussinessException;
import br.ufpr.sportease.apigateway.model.dto.login.LoginRequest;
import br.ufpr.sportease.apigateway.model.dto.login.LoginResponse;
import br.ufpr.sportease.apigateway.model.entity.Usuario;
import br.ufpr.sportease.apigateway.security.TokenService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/login")
@CrossOrigin(origins ="*")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final MsCadastrosClient msCadastrosClient;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService, MsCadastrosClient msCadastrosClient) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.msCadastrosClient = msCadastrosClient;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> logar(@RequestBody @Valid LoginRequest loginRequest)  {
        //A classe AuthenticationManager chama o método de LoginService por baixo dos panos

        Usuario usuario = null;
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken
                    (loginRequest.getLogin(), loginRequest.getSenha());
            var authentication = authenticationManager.authenticate(token);
            usuario = (Usuario) authentication.getPrincipal();

        } catch (InternalAuthenticationServiceException e) {
            throw new BadCredentialsException("Usuário inexistente ou senha inválida");
        }

        if(Boolean.TRUE.equals(usuario.getBloqueada())) {
            throw new BussinessException("Essa conta está bloqueada");
        }

        if(Boolean.FALSE.equals(usuario.getAtiva())) {
            throw new BussinessException("Essa conta não está ativa");
        }

        //buscar idPessoa
        Object admId;
        Object clienteId;

        try {
            admId = msCadastrosClient.buscarIdAdmPorIdUsuario(usuario.getId());
        } catch (HttpClientErrorException ex) {
            admId = null;
        }
        try {
            clienteId = msCadastrosClient.buscarIdClientePorIdUsuario(usuario.getId());
        } catch (HttpClientErrorException ex) {
            clienteId = null;
        }
        Object idPessoa = (admId != null) ? admId : clienteId;

        assert idPessoa != null;
        var tokenJWT = tokenService.gerarTokenPadrao(usuario, Long.parseLong(idPessoa.toString()));


        return ResponseEntity.ok(new LoginResponse(tokenJWT));
    }
}
