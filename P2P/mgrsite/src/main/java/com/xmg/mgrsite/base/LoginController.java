package com.xmg.mgrsite.base;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台登陆
 */
@Controller
public class LoginController {
    @Autowired
    ILogininfoService logininfoService;
    @Autowired
    IIplogService iplogService;


    /**
     * 后台登陆功能
     * @param username
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public JSONResult login (String username, String password, HttpServletRequest request){
        JSONResult jsonResult = new JSONResult();
        Logininfo logininfo = logininfoService.login(username,password,request,Logininfo.USER_MANAGER);
        if(logininfo == null){
            jsonResult.setMsg("账号或密码错误");
            jsonResult.setSuccess(false);
            return jsonResult;
        }

        jsonResult.setSuccess(true);
        jsonResult.setMsg("登陆成功");
        return jsonResult;
    }

    @RequestMapping("index")
    public String index(Model model){
        Logininfo logininfo = UserContext.getCurrent();

        return "main";
    }



}
