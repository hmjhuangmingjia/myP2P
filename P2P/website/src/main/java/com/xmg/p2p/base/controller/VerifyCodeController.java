package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.service.IVeryfiCodeService;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class VerifyCodeController {
    @Autowired
    IVeryfiCodeService veryfiCodeService;

    /**
     * 发手机验证码
     * @param phoneNumber
     * @return
     */
    @RequireLogin
    @RequestMapping("sendVerifyCode")
    @ResponseBody
    public JSONResult sendVerifyCode(String phoneNumber){
        JSONResult jsonResult =new JSONResult();
        try {
            veryfiCodeService.sendVeryfiCode(phoneNumber);
            jsonResult.setSuccess(true);
            return jsonResult;
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);
            return jsonResult;
        }

    }
}
