package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIMessages> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        APIMessages messages = APIMessages.builder().success(false).message(e.getMessage()).build();

        return ResponseEntity.badRequest().body(messages);
    }
}
