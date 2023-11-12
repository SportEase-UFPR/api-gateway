package br.ufpr.sportease.apigateway.service;

import br.ufpr.sportease.apigateway.exceptions.BussinessException;
import br.ufpr.sportease.apigateway.exceptions.EntityNotFoundException;
import br.ufpr.sportease.apigateway.model.dto.usuario.*;
import br.ufpr.sportease.apigateway.model.entity.Usuario;
import br.ufpr.sportease.apigateway.repository.UsuarioRepository;
import br.ufpr.sportease.apigateway.security.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public UsuarioService(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    public void excluirUsuarioPorId(Long idUsuario, String tokenApi) {
        //validar token
        tokenService.validarTokenMs(tokenApi);
        repository.deleteById(idUsuario);
    }

    public AtivarContaResponse ativarConta(String tokenAtivacaoConta) {
        //recuperar e validar o id do usuário do token
        String idUsuario = tokenService.validarTokenUserERecuperarSubject(tokenAtivacaoConta);

        //buscar usuário pelo id
        Usuario usuario = repository.findById(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(USUARIO_NAO_ENCONTRADO));

        //verificar se a conta está ativa
        if(Boolean.TRUE.equals(usuario.getAtiva())) {
            throw new BussinessException("Essa conta já está ativa");
        }

        //ativar conta
        usuario.setAtiva(true);
        repository.save(usuario);

        return AtivarContaResponse.builder()
                .idUsuario(usuario.getId())
                .mensagem("Conta ativada com sucesso!")
                .build();
    }

    public AlterarSenhaResponse alterarSenha(AlterarSenhaRequest alterarSenhaRequest) {
        //recuperar do id do usuário do token
        String idUsuario = tokenService.getSubject(alterarSenhaRequest.getTokenUsuario());

        //recuperar usuário pelo id
        Usuario usuario = repository.findById(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException(USUARIO_NAO_ENCONTRADO));

        //verificar se a conta está ativa
        if(!Boolean.TRUE.equals(usuario.getAtiva())) {
            throw new BussinessException("A conta não está ativa");
        }

        //atualizar nova senha
        usuario.setSenha(new BCryptPasswordEncoder().encode(alterarSenhaRequest.getNovaSenha()));
        repository.save(usuario);

        return AlterarSenhaResponse.builder()
                .idConta(idUsuario)
                .mensagem("Senha alterada com sucesso")
                .build();

    }

    public Void bloquearConta(Long idUsuario, BloquearContaRequest request) {
        var usuario = repository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException(USUARIO_NAO_ENCONTRADO));
        usuario.setBloqueada(true);
        usuario.setMotivoBloqueio(request.getJustificativa());
        repository.save(usuario);

        //todo mandar email pro usuário informando que a conta foi bloqueada

        return null;
    }

    public Void desbloquearConta(Long idUsuario) {
        var usuario = repository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException(USUARIO_NAO_ENCONTRADO));
        usuario.setBloqueada(false);
        usuario.setMotivoBloqueio(null);
        repository.save(usuario);

        //todo mandar email pro usuário informando que a conta foi desbloqueada

        return null;
    }

    public List<StatusBloqueioContaResponse> buscarStatusBloqueioContas() {
        var listaUsuarios = repository.findAll();
        var listaStatusBloqueioContaResponse = new ArrayList<StatusBloqueioContaResponse>();

        listaUsuarios.forEach(usuario -> listaStatusBloqueioContaResponse.add(new StatusBloqueioContaResponse(usuario)));

        return listaStatusBloqueioContaResponse;
    }
}
