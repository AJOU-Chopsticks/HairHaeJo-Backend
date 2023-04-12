package Chopsticks.HairHaeJoBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    // test
    @PostMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok().build();
    }
}
