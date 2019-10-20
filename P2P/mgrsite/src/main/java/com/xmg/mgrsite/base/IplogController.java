package com.xmg.mgrsite.base;

import com.xmg.p2p.base.domain.Logininfo;
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
public class IplogController {
    @Autowired
    ILogininfoService logininfoService;
    @Autowired
    IIplogService iplogService;



    @RequestMapping("ipLog")
    public String iplog (Model model,IplogQueryObject qo){
        PageResult pageResult = iplogService.query(qo);
        model.addAttribute("pageResult",pageResult);
        model.addAttribute("qo",qo);
        return "iplog/list";
    }


}
