package com.idms.controller;

import com.idms.client.IDMSClient;
import com.idms.helper.FeignHelper;
import com.idms.model.request.AccountFilterRequest;
import com.idms.model.request.LoginRequestDTO;
import com.idms.model.response.AccountDTO;
import com.idms.model.response.AccountRowDTO;
import com.idms.model.response.AuthenticateResult;
import com.idms.model.response.CommonResponse;
import com.idms.service.AccountService;
import com.idms.service.AuthService;
import com.idms.service.IDMSService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/idms")
public class IDMSController {

    @Autowired
    private AuthService authService;


    @Autowired
    private IDMSService idmsService;

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/loadIDMSData")
    public ResponseEntity<CommonResponse<String>> fetchData(){
        String response = idmsService.fetchData();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK.value(),response));
    }

    @PostMapping(path = "/getData")
    public ResponseEntity<CommonResponse<List<AccountRowDTO>>> filterAccountRowDTO(@Valid @RequestBody AccountFilterRequest filterRequest){
        List<AccountRowDTO> response = accountService.filterAccountRowDTO(filterRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(HttpStatus.OK.value(),response,"Success"));
    }


}
