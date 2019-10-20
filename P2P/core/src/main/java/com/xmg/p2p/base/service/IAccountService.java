package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Account;

public interface IAccountService {

    void update (Account account);

    void add(Account account);

    Account get(Long id);

    Account getCurrent();
}
