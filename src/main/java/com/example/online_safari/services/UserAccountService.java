package com.example.online_safari.services;

import com.example.online_safari.dto.UserAccountDto;
import com.example.online_safari.model.UserAccount;
import com.example.online_safari.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAccountService {
    Response<UserAccount> createUpdateUser(UserAccountDto userAccountDto);

    Response<UserAccount> deleteUserAccount(String uuid);

    Response<UserAccount> getUserByUuid(String uuid);

    Page<UserAccount> getCustomers(Pageable pageable);

    Page<UserAccount>  getOfficials (Pageable pageable);
}
