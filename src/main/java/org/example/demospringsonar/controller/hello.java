package org.example.demospringsonar.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class hello {

    @GetMapping("/hello")
    public ResponseEntity sayHello(@RequestParam(value = "myName") String name) {
        return ResponseEntity.ok().body("Hello " + name);
    }

}
