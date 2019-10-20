package com.xmg.p2p.base.controller;


import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BasicInfoController {
    @Autowired
    IUserinfoService userinfoService;
    @Autowired
    ISystemDictionaryService systemDictionaryService;

    @RequireLogin
    @RequestMapping("basicInfo")
    public String basicInfo(Model model){
        Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());

        model.addAttribute("incomeGrades",systemDictionaryService.listByParentSn("incomeGrade"));
        model.addAttribute("kidCounts",systemDictionaryService.listByParentSn("kidCount"));
        model.addAttribute("educationBackgrounds",systemDictionaryService.listByParentSn("educationBackground"));
        model.addAttribute("houseConditions",systemDictionaryService.listByParentSn("houseCondition"));
        model.addAttribute("marriages",systemDictionaryService.listByParentSn("marriage"));
        model.addAttribute("userFileType",systemDictionaryService.listByParentSn("userFileType"));

        System.out.println(systemDictionaryService.listByParentSn("incomeGrade"));


        model.addAttribute("userinfo",userinfo);
        return "userInfo";
    }

    @RequestMapping("basicInfo_save")
    @ResponseBody
    @RequireLogin
    public JSONResult save (Userinfo userinfo){

        userinfoService.updateBasicInfo(userinfo);

        return new JSONResult(true,"保存成功");
    }
}
