package org.varunmatangi.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/default")
public class DefaultController {

    @GetMapping("/")
    public ResponseEntity<String> greet(){
        return ResponseEntity.ok("Hello Welcome to Spring Security");
    }
}
