package com.idms.controller;

import com.idms.client.IDMSClient;
import com.idms.helper.FeignHelper;
import com.idms.model.request.LoginRequestDTO;
import com.idms.model.response.AuthenticateResult;
import com.idms.model.response.CommonResponse;
import com.idms.service.AuthService;
import feign.Response;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/public")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(path = "/auth/user")
    public ResponseEntity<CommonResponse<String>> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest){
        String token = authService.validateAndProcessLogin(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK.value(),token,null));
    }


}
