package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.Service.IBidrequestService;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站首页
 */
@Controller
public class IndexController {
    @Autowired
    IBidrequestService bidrequestService;

    @RequestMapping("index")
    public String index (Model model){
        model.addAttribute("bidRequests",bidrequestService.listIndex(5));
        return "main";
    }

    @RequestMapping("invest")
    public String invest (){
        return "invest";
    }

    @RequestMapping("invest_list")
    public String investList(Model model,BidRequestQueryObject qo){
        PageResult pageResult = bidrequestService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "invest_list";
    }
}
