package com.cernestoc.controller;

import com.cernestoc.controller.dtos.LogInRequest;
import com.cernestoc.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cernestoc.controller.dtos.SignUpRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Object> singup(@RequestBody SignUpRequest request) throws Exception{
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LogInRequest request) throws Exception{
        log.info("Entrando en controlador de login");
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
