package br.ufpr.sportease.apigateway.security;

import br.ufpr.sportease.apigateway.exceptions.TokenInvalidoException;
import br.ufpr.sportease.apigateway.model.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static constants.HorarioBrasil.HORA_ATUAL;

@Slf4j
@Service
public class TokenService {

    @Value("${api.security.token.user.secret}")
    private String userSecret;

    @Value("${api.security.token.apigateway.secret}")
    private String apiGatewaySecret;

    @Value("${api.security.token.mscadastro.secret}")
    private String msCadastroSecret;

    @Value("${api.gateway.issuer}")
    private String apiGatewayIssuer;

    @Value("${api.user.issuer}")
    private String apiUserIssuer;

    @Value("${api.mscadastro.issuer}")
    private String msCadastroIssuer;


    public String gerarTokenPadrao(Usuario usuario, Long idPessoa) {
        var algoritmo = Algorithm.HMAC256(userSecret);
        return JWT.create()
                .withIssuer(apiUserIssuer) //quem gerou o token
                .withSubject(usuario.getId().toString()) //adiciona o id do usuário ao token
                .withClaim("userProfile", usuario.getNivelAcesso().toString()) //aqui dá pra adicionar outros atributos se necessário
                .withClaim("idPessoa", idPessoa.toString()) //adiciona o id do cliente ou adm ao token
                .withExpiresAt(dataExpiracao(120)) //data da expiração
                .sign(algoritmo); //assinatura
    }

    public String gerarTokenApiGateway() {
        var algoritmo = Algorithm.HMAC256(apiGatewaySecret);
        return JWT.create()
                .withIssuer(apiGatewayIssuer)
                .withSubject(apiGatewayIssuer)
                .withExpiresAt(dataExpiracao(20)) //data da expiração
                .sign(algoritmo); //assinatura

    }

    //validar o token e recupera o subject (no nosso caso, o id do usuário)
    public String validateTokenAndGetSubject(String tokenJWT) {
        var tokenFormatado = removerPrefixoToken(tokenJWT);
        try {
            var algoritmo = Algorithm.HMAC256(userSecret);
            return JWT.require(algoritmo)//verifica se a senha está correta
                    .withIssuer(apiUserIssuer) //verifica se quem gerou o token foi a própria API SportEase
                    .build()
                    .verify(tokenFormatado) //validação
                    .getSubject(); //recupera o subject do token
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    //valida se o token foi gerado pela api msCadatro
    public void validarTokenApiMsCadastro(String tokenApi) {
        var tokenFormatado = removerPrefixoToken(tokenApi);
        try {
            var algoritmo = Algorithm.HMAC256(msCadastroSecret);
            JWT.require(algoritmo)
                    .withIssuer(msCadastroIssuer)
                    .build()
                    .verify(tokenFormatado);
        } catch (JWTVerificationException ex) {
            log.error(ex.getMessage());
            throw new TokenInvalidoException("Token JWT inválido ou expirado");
        }
    }

    //recupera um issuer do token
    public String getIssuer(String tokenJWT, String issuer) {
        return JWT.decode(tokenJWT).getClaim(issuer).asString();
    }

    //recupera o subject do token
    public String getSubject(String tokenJWT) {
        return JWT.decode(tokenJWT).getSubject();
    }

    private Instant dataExpiracao(Integer minutes) {
        return HORA_ATUAL.plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }

    public String removerPrefixoToken(String token) {
        return token.replace("Bearer ", "");
    }


}
