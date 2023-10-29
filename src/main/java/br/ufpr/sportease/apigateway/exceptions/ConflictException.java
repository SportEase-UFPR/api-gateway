package br.ufpr.sportease.apigateway.exceptions;

public class ConflictException extends RuntimeException{
    public ConflictException(String msg) {
        super(msg);
    }
}
