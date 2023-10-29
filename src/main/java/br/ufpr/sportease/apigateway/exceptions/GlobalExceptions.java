package br.ufpr.sportease.apigateway.exceptions;

import br.ufpr.sportease.apigateway.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<StandardError> httpClientErrorExceptionHandlerMethod(
            HttpClientErrorException e, HttpServletRequest request) {

        StandardError se = new StandardError(
                LocalDateTime.now(),
                e.getStatusCode().value() ,
                HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase(),
                //e.getMessage(),
                StringUtils.extrairMensagemDeErro(e.getMessage()),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.valueOf(e.getStatusCode().value())).body(se);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<StandardError> tokenInvalidoExceptionHandlerMethod(
            TokenInvalidoException e, HttpServletRequest request) {

        StandardError se = new StandardError(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(se);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<StandardError> conflictHandlerMethod(
            ConflictException e, HttpServletRequest request) {

        StandardError se = new StandardError(
                LocalDateTime.now(), 409, HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(se);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundHandlerMethod(
            EntityNotFoundException e, HttpServletRequest request) {

        StandardError se = new StandardError(
                LocalDateTime.now(), 404, HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(se);
    }

    @ExceptionHandler(BussinessException.class)
    public ResponseEntity<StandardError> bussinessExceptionHandlerMethod(
            BussinessException e, HttpServletRequest request) {

        StandardError se = new StandardError(
                LocalDateTime.now(), 412, HttpStatus.PRECONDITION_FAILED.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(se);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> bussinessExceptionHandlerMethod(
            BadCredentialsException e, HttpServletRequest request) {

        StandardError se = new StandardError(
                LocalDateTime.now(), 401, HttpStatus.UNAUTHORIZED.getReasonPhrase(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(se);
    }

}
