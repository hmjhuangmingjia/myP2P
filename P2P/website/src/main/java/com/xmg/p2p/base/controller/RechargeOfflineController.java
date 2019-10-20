package com.xmg.p2p.base.controller;

import com.alibaba.fastjson.JSON;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.Service.IPlatformBankInfoService;
import com.xmg.p2p.business.Service.IRechargeOfflineService;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RechargeOfflineController {

    @Autowired
    IPlatformBankInfoService platformBankInfoService;
    @Autowired
    IRechargeOfflineService rechargeOfflineService;

    @RequestMapping("recharge")
    public String recharge (Model model){
        model.addAttribute("banks",platformBankInfoService.listAll());
        return "recharge";
    }

    @RequestMapping("recharge_save")
    @ResponseBody
    public JSONResult rechargeApply (RechargeOffline rechargeOffline){
        rechargeOfflineService.apply(rechargeOffline);
        return new JSONResult(true,"操作成功");
    }

    @RequireLogin
    @RequestMapping("recharge_list")
    public String rechargeList(
            @ModelAttribute("qo") RechargeOfflineQueryObject qo, Model model) {
        qo.setApplierId(UserContext.getCurrent().getId());
        model.addAttribute("pageResult", this.rechargeOfflineService.query(qo));
        return "recharge_list";
    }
}
