package br.ufpr.sportease.apigateway.service;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.exceptions.ConflictException;
import br.ufpr.sportease.apigateway.exceptions.EntityNotFoundException;
import br.ufpr.sportease.apigateway.model.dto.adm.*;
import br.ufpr.sportease.apigateway.model.entity.Usuario;
import br.ufpr.sportease.apigateway.model.enums.NivelAcesso;
import br.ufpr.sportease.apigateway.repository.UsuarioRepository;
import br.ufpr.sportease.apigateway.security.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdmService {

    private final UsuarioRepository repository;
    private final MsCadastrosClient msCadastrosClient;
    private final TokenService tokenService;


    public AdmService(UsuarioRepository repository, MsCadastrosClient msCadastrosClient, TokenService tokenService) {
        this.repository = repository;
        this.msCadastrosClient = msCadastrosClient;
        this.tokenService = tokenService;
    }

    public Object alterarEmailAdm(AdmAlterarEmailRequest request) {
        //recuperar do id do usuário do token
        String idUsuario = tokenService.getSubject(request.getTokenUsuarioComNovoEmail());

        //recuperar novo email do token
        String novoEmail = tokenService.getIssuer(request.getTokenUsuarioComNovoEmail(), "email");

        //recuperar usuário
        Usuario usuario = repository.findById(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("Adm não encontrado"));

        //alterar usuário
        usuario.setLogin(novoEmail);
        repository.save(usuario);

        //alterar Adm
        return msCadastrosClient.alterarEmailAdm(request);
    }

    public Object criarAdm(AdmCriacaoRequest request) {
        //Caso uma conta com o email já exista, lançará exceção
        if(Boolean.TRUE.equals(repository.existsByLogin(request.getEmail()))) {
            throw new ConflictException("Email já cadastrado");
        }

        //Criação de uma nova conta
        Usuario novoUsuario = new Usuario(request.getEmail(), request.getSenha(), NivelAcesso.ROLE_ADM);
        repository.save(novoUsuario);

        //Criação de um novo adm
        AdmCriacaoRequestMsCadastro requestMsCadastro = new AdmCriacaoRequestMsCadastro(request);
        requestMsCadastro.setIdUsuario(novoUsuario.getId());
        return msCadastrosClient.criarAdm(requestMsCadastro);
    }

    public Object editarDadosAdm(String token, AdmAlteracaoRequest request) {
        //recuperar o id do usuário do token
        String idUsuario = tokenService.getSubject(token);

        //recuperar o usuário
        Usuario usuario = repository.findById(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));


        if(request.getEmail() != null &&
                !request.getEmail().equals(usuario.getLogin()) &&
                Boolean.TRUE.equals(repository.existsByLogin(request.getEmail()))
        ) {
            throw new ConflictException("Email já existente");
        }

        //alterar Senha
        if(StringUtils.isNotBlank(request.getSenha())) {
            usuario.setSenha(new BCryptPasswordEncoder().encode(request.getSenha()));
            repository.save(usuario);
        }

        //alterar adm
        if(StringUtils.isNotBlank(request.getNome()) || StringUtils.isNotBlank(request.getEmail())) {
            return msCadastrosClient.editarDadosAdm(token, new AdmAlteracaoRequestMsCadastro(request));
        }

        return AdmAlteracaoResponse.builder().mensagem("Dados do adm alterado com sucesso");

    }
}
