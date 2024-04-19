package com.edu.serviceFarma.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Object> home(){
        return ResponseEntity.ok().body("Bem Vindo à minha aplicação Service Farma!");
    }
}
