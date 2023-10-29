package br.ufpr.sportease.apigateway.service;

import br.ufpr.sportease.apigateway.client.MsCadastrosClient;
import br.ufpr.sportease.apigateway.exceptions.ConflictException;
import br.ufpr.sportease.apigateway.exceptions.EntityNotFoundException;
import br.ufpr.sportease.apigateway.model.dto.cliente.*;
import br.ufpr.sportease.apigateway.model.entity.Usuario;
import br.ufpr.sportease.apigateway.model.enums.NivelAcesso;
import br.ufpr.sportease.apigateway.repository.UsuarioRepository;
import br.ufpr.sportease.apigateway.security.TokenService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final UsuarioRepository repository;
    private final MsCadastrosClient msCadastrosClient;
    private final TokenService tokenService;


    public ClienteService(UsuarioRepository repository, MsCadastrosClient msCadastrosClient, TokenService tokenService) {
        this.repository = repository;
        this.msCadastrosClient = msCadastrosClient;
        this.tokenService = tokenService;
    }

    @Transactional
    public Object criarCliente(ClienteCriacaoRequest request) {
        //Caso uma conta com o email já exista, lançará exceção
        if(Boolean.TRUE.equals(repository.existsByLogin(request.getEmail()))) {
            throw new ConflictException("Email já cadastrado");
        }

        //Criação de uma nova conta
        Usuario novoUsuario = new Usuario(request.getEmail(), request.getSenha(), NivelAcesso.ROLE_CLIENTE);
        repository.save(novoUsuario);

        //Criação de um novo cliente
        ClienteCriacaoRequestMsCadastro requestMsCadastro = new ClienteCriacaoRequestMsCadastro(request);
        requestMsCadastro.setIdUsuario(novoUsuario.getId());
        return msCadastrosClient.criarCliente(requestMsCadastro);

    }

    @Transactional
    public Object atualizarDadosCliente(String token, ClienteAlteracaoRequest request) {
        //recuperar o id do usuário do token
        String idUsuario = tokenService.getSubject(token);

        //recuperar o usuário
        Usuario usuario = repository.findById(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        //verificar se email já existe
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

        //alterar Cliente
        if(StringUtils.isNotBlank(request.getNome()) || StringUtils.isNotBlank(request.getEmail())) {
            return msCadastrosClient.atualizarDadosCliente(token, new ClienteAlteracaoRequestMsCadastro(request));
        }

        return ClienteAlteracaoResponse.builder().mensagem("Dados do cliente alterado com sucesso");
    }

    @Transactional
    public Object alterarEmailCliente(ClienteAlterarEmailRequest request) {
        //recuperar o id e o email do token
        String idUsuario = tokenService.getSubject(request.getTokenUsuarioComNovoEmail());
        String novoEmail = tokenService.getIssuer(request.getTokenUsuarioComNovoEmail(), "email");

        //recuperar o usuário
        Usuario usuario = repository.findById(Long.parseLong(idUsuario))
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        //atualizar usuário
        usuario.setLogin(novoEmail);
        repository.save(usuario);

        //atualiza email do cliente
        return msCadastrosClient.alterarEmailCliente(request);
    }
}
