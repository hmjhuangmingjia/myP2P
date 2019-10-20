package com.xmg.mgrsite.base;


import com.xmg.p2p.business.Service.IPlatformBankInfoService;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PlatformBankInfoController {
    @Autowired
    IPlatformBankInfoService platformBankInfoService;

    @RequestMapping("companyBank_list")
    public String platformBankinfoList(
            @ModelAttribute("qo") PlatformBankInfoQueryObject qo, Model model) {
        model.addAttribute("pageResult", this.platformBankInfoService.query(qo));
        return "platformbankinfo/list";
    }

    /**
     * 修改或保存
     */
    @RequestMapping("companyBank_update")
    public String update(PlatformBankInfo bankInfo) {
        this.platformBankInfoService.saveOrUpdate(bankInfo);
        return "redirect:/companyBank_list.do";
    }
}
