package com.encore.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

//ControllerAdvice 와 exceptionHandler를 통해 예외처리의 공통화 로직 작성 가능
@ControllerAdvice
@Slf4j
public class CommonException {
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Map<String,Object>> entityNotFoundHandler(EntityNotFoundException e){
            log.error("Handler EntityNotFoundException : " + e.getMessage());

            return this.erroresponsemessage(HttpStatus.NOT_FOUND, e.getMessage());
        }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> IleagalAragumentHandler(IllegalArgumentException e){
        log.error("Handler IllegalArgumentException : " + e.getMessage());

        return this.erroresponsemessage(HttpStatus.BAD_REQUEST,e.getMessage());
    }
    private  ResponseEntity<Map<String,Object>> erroresponsemessage(HttpStatus status, String message){
        Map<String, Object> body = new HashMap<>();
        body.put("status",Integer.toString(status.value()));
        body.put("status code",status.getReasonPhrase());
        body.put("error message",message);
        return new ResponseEntity<>(body,status);
    }
}
