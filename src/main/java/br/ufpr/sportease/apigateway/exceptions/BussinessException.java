package br.ufpr.sportease.apigateway.exceptions;

public class BussinessException extends RuntimeException{
    public BussinessException(String msg) {
        super(msg);
    }
}
