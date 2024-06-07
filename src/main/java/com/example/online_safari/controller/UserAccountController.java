package com.example.online_safari.controller;

import com.example.online_safari.dto.UserAccountDto;
import com.example.online_safari.model.UserAccount;
import com.example.online_safari.services.UserAccountService;
import com.example.online_safari.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;


    @PostMapping(path = "/create-update")
    public ResponseEntity<?> createUser(@RequestBody UserAccountDto userAccountDto){
        Response<UserAccount> response = userAccountService.createUpdateUser(userAccountDto);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping(path = "/get-customers")
    public ResponseEntity<?> getCustomers(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                          @RequestParam(value = "size", defaultValue = "25")Integer size){
        Pageable pageable = PageRequest.of(page,size);
        Page<UserAccount> customers = userAccountService.getCustomers(pageable);

        return ResponseEntity.ok()
                .body(customers);
    }

    @GetMapping(path = "/get-officials")
    public ResponseEntity<?> getOfficials(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                          @RequestParam(value = "size", defaultValue =  "25")Integer size){
        Pageable pageable = PageRequest.of(page,size);

        Page<UserAccount> officials = userAccountService.getOfficials(pageable);

        return ResponseEntity.ok()
                .body(officials);
    }

    @GetMapping(path = "/get/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable(value = "uuid") String uuid){
        Response<UserAccount> userAccountById = userAccountService.getUserByUuid(uuid);

        return ResponseEntity.ok()
                .body(userAccountById);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "uuid") String uuid){
        Response<UserAccount>  deleteUser = userAccountService.deleteUserAccount(uuid);

        return ResponseEntity.ok()
                .body(deleteUser);
    }


}
