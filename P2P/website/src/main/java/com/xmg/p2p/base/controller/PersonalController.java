package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IIplogService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonalController {
    @Autowired
    IAccountService accountService;
    @Autowired
    IUserinfoService userinfoService;
    @Autowired
    IIplogService iplogService;


    @RequireLogin
    @RequestMapping("sendEmail")
    @ResponseBody
    public JSONResult sendEmail(){
        JSONResult jsonResult = new JSONResult();
        jsonResult.setSuccess(true);
        jsonResult.setMsg("发邮件待开发");
        return jsonResult;
    }

    @RequireLogin
    @RequestMapping("bindPhone")
    @ResponseBody
    public JSONResult bindPhone(String phoneNumber,String verifyCode){
        JSONResult json = new JSONResult();
        try {
            this.userinfoService.bindPhone(phoneNumber, verifyCode);
            json.setSuccess(true);
            json.setMsg("绑定成功");
        } catch (RuntimeException re) {
            json.setSuccess(false);
            json.setMsg(re.getMessage());
        }
        return json;
    }

    @RequireLogin
    @RequestMapping("personal")
    public String personalCenter(Model mode){
        Logininfo logininfo = UserContext.getCurrent();
        Account account = accountService.get(logininfo.getId());
        Userinfo userinfo = userinfoService.get(logininfo.getId());
        mode.addAttribute("account",account);
        mode.addAttribute("userinfo",userinfo);
        return "personal";
    }

    @RequestMapping("ipLog")
    public String iplog(Model model, IplogQueryObject qo){
        qo.setUsername(UserContext.getCurrent().getUsername());
        PageResult pageResult = iplogService.query(qo);
        model.addAttribute("qo",qo);
        model.addAttribute("pageResult",pageResult);
        return "iplog_list";
    }
}
