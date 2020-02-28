package com.spring.security.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicApiController {

    @GetMapping("/test1")
    public ResponseEntity<String> test1(){
        return new ResponseEntity<>("test1",HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2(){
        return new ResponseEntity<>("test2",HttpStatus.OK);
    }
}
