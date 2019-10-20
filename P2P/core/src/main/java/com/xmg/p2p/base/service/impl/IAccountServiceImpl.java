package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.mapper.AccountMapper;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IAccountServiceImpl implements IAccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public void update(Account account) {
        int ret = accountMapper.updateByPrimaryKey(account);
        if(ret == 0){
            throw new RuntimeException("乐观锁失败:Account:" + account.getId());
        }
    }

    @Override
    public void add(Account account) {
        int insert = accountMapper.insert(account);
        if(insert == 0){
            throw new RuntimeException("插入用户帐户表失败");
        }
    }

    @Override
    public Account get(Long id) {
        return accountMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取用户账户信息
     * @return
     */
    @Override
    public Account getCurrent() {
        Long id = UserContext.getCurrent().getId();
        Account account = this.get(id);
        return account;
    }
}
