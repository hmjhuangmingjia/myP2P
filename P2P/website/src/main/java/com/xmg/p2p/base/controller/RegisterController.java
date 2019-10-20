package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
    @Autowired
    ILogininfoService logininfoService;

    @RequestMapping("/register")
    @ResponseBody
    public JSONResult register(String username, String password){
        JSONResult jsonResult = new JSONResult();
        try{
            logininfoService.register(username,password);
            jsonResult.setMsg("注册成功！");
            jsonResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();
            jsonResult.setSuccess(false);
            jsonResult.setMsg(message);
        }
        return jsonResult;
    }

    @RequestMapping("checkUsername")
    @ResponseBody
    public boolean checkUsername(String username){

        return !logininfoService.checkUsername(username);
    }

    @RequestMapping("login")
    @ResponseBody
    public JSONResult login(String username , String password, HttpServletRequest request){
        JSONResult jsonResult = new JSONResult();
                Logininfo logininfo = logininfoService.login(username,password,request,Logininfo.USER_CLIENT);
                if(logininfo == null){
                    jsonResult.setMsg("账号或密码错误");
                    jsonResult.setSuccess(false);
                    return jsonResult;
                }

                jsonResult.setSuccess(true);
                jsonResult.setMsg("登陆成功");
                return jsonResult;

    }
}
