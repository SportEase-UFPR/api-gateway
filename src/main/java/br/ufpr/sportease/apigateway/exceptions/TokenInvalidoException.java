package br.ufpr.sportease.apigateway.exceptions;

public class TokenInvalidoException extends RuntimeException{
    public TokenInvalidoException(String msg) {
        super(msg);
    }
}
