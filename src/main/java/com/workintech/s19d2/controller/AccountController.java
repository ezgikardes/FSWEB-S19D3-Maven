package com.workintech.s19d2.controller;

import com.workintech.s19d2.entity.Account;
import com.workintech.s19d2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> findAll(){
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account findById(@PathVariable long id){
        return accountService.findById(id);
    }

    @PostMapping
    public Account save(@RequestBody Account account){
        return accountService.save(account);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable long id, @RequestBody Account account){
        Account accountToUpdate = accountService.findById(id);
        accountToUpdate.setName(account.getName());

        return accountToUpdate;
    }

}