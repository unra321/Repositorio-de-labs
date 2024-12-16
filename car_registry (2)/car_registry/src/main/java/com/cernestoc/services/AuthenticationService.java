package com.cernestoc.services;

import com.cernestoc.controller.dtos.JwtResponse;
import com.cernestoc.controller.dtos.LogInRequest;
import com.cernestoc.controller.dtos.SignUpRequest;

public interface AuthenticationService {

    public JwtResponse signup(SignUpRequest request) throws Exception;

    public JwtResponse login(LogInRequest request) throws Exception;
}
