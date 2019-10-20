package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.mapper.IplogMapper;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.LogininfoMapper;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.MD5;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LogininfoServiceImpl implements ILogininfoService {
    @Autowired
    LogininfoMapper logininfoMapper;
    @Autowired
    IUserinfoService userinfoService;
    @Autowired
    IAccountService accountService;
    @Autowired
    IplogMapper iplogMapper;

    @Override
    public void register(String username, String password) {
        int count = logininfoMapper.getCountByUsername(username);

        if(count>0){
            throw new RuntimeException("用户名已存在");
        }

        //向用户登录表插入数据
        Logininfo logininfo = new Logininfo();
        logininfo.setUsername(username);
        String pwd = MD5.encode(password);
        logininfo.setPassword(pwd);
        logininfo.setState(Logininfo.STATE_NORMAL);
        logininfo.setUsertype(Logininfo.USER_CLIENT);
        logininfoMapper.insert(logininfo);

        //向用户数据表插入数据
        Userinfo userinfo = new Userinfo();
        userinfo.setId(logininfo.getId());
        userinfoService.add(userinfo);

        //向用户账户表插入数据
        Account account = new Account();
        account.setId(logininfo.getId());
        accountService.add(account);

    }

    /**
     * 校验用户名是否有重复
     * @param username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        int count = logininfoMapper.getCountByUsername(username);
        if(count>0){
            return true;
        }else {
            return false;
        }    }

    /**
     * 登陆日志功能
     * @param username
     * @param password
     * @param request
     * @param userType
     * @return
     */
    @Override
    public Logininfo login(String username, String password, HttpServletRequest request, int userType) {

        Logininfo logininfo = logininfoMapper.login(username, MD5.encode(password),userType);
        Iplog iplog = new Iplog();

        iplog.setUsername(username);
        iplog.setLogintime(new Date());
        iplog.setIp(request.getRemoteAddr());
        iplog.setUserType(userType);
        if(logininfo == null){
            iplog.setState(Iplog.STATE_FAILED);
        }else {
            iplog.setState(Iplog.STATE_SUCCESS);
        }
        iplogMapper.insert(iplog);


        //账号密码正确
        //把账号信息放到session中
        UserContext.putCurrent(logininfo);

        return logininfo;
    }

    @Override
    public void initAdmin() {
        //查询是否有管理员
        int count =logininfoMapper.selectByUserType(Logininfo.USER_MANAGER);
        //如果没有就创建一个默认的管理员
        if(count > 0 ){
            return;
        }
        Logininfo admin = new Logininfo();
        admin.setUsername(BidConst.DEFAULT_ADMIN_NAME);
        admin.setPassword(MD5.encode(BidConst.DEFAULT_ADMIN_PASSWORD));
        admin.setState(Logininfo.STATE_NORMAL);
        admin.setUsertype(Logininfo.USER_MANAGER);

        logininfoMapper.insert(admin);

    }


}
