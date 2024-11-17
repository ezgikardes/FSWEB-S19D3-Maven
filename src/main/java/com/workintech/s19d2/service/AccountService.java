package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    List<Account> findAll();
    Account findById(long id);
    Account save(Account account);
    Account delete(long id);
}
