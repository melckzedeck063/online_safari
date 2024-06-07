package com.example.online_safari.services;


import com.example.online_safari.dto.LoginDto;
import com.example.online_safari.dto.LoginResponseDto;
import com.example.online_safari.dto.UserAccountDto;
import com.example.online_safari.model.UserAccount;
import com.example.online_safari.utils.Response;

public interface AuthService {

    Response<LoginResponseDto> login(LoginDto loginDto);

    Response registerUser(UserAccountDto userAccountDto);
    Response<LoginResponseDto> revokeToken(String refreshToken);
    Response<Boolean> forgetPassword(String email);
    Response<UserAccount> getLoggedUser();


}

