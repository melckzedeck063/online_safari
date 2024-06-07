package com.example.online_safari.controller;

import com.example.online_safari.dto.LoginDto;
import com.example.online_safari.dto.LoginResponseDto;
import com.example.online_safari.dto.UserAccountDto;
import com.example.online_safari.services.AuthService;
import com.example.online_safari.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        Response<LoginResponseDto> response = authService.login(loginDto);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register (@RequestBody UserAccountDto userAccountDto) {
        Response<String> stringResponse = authService.registerUser(userAccountDto);
        return ResponseEntity.ok().body(stringResponse);
    }
//
//    @GetMapping(path = "/activate-account")
//    public ResponseEntity<?> activateAccount(@RequestParam(value = "code")String code) {
//        Response<String> stringResponse = authService.activateAccountThroughOTP(code);
//        return ResponseEntity.ok()
//                .body(stringResponse);
//    }

//
//    @GetMapping(path = "/code/request-new")
//    public ResponseEntity<?> requestNew(@PathParam(value = "phone")String phone){
//        Response<String> stringResponse = authService.requestNewOtp(phone);
//        return ResponseEntity.ok()
//                .body(stringResponse);
//    }


}

