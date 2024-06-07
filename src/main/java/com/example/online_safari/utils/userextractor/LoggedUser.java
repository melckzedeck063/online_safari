package com.example.online_safari.utils.userextractor;


import com.example.online_safari.model.UserAccount;

public interface LoggedUser {

    UserInfo getInfo();

    UserAccount getUser();
}
