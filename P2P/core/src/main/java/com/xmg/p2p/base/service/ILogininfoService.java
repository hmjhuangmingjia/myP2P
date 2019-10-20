package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Logininfo;

import javax.servlet.http.HttpServletRequest;

public interface ILogininfoService {
    /**
     * 注册功能
     * @param username
     * @param password
     */
    void register(String username,String password);

    boolean checkUsername(String username);

    Logininfo login(String username, String password, HttpServletRequest request, int userClient);


    /**
     * 初始化超级管理员
     */
    void initAdmin();
}
