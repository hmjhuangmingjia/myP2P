package com.xmg.mgrsite.base;

import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RealAuthController {
    @Autowired
    IRealAuthService realAuthService;


    @RequestMapping("realAuth_audit")
    @ResponseBody
    public JSONResult realAuthAudit(Long id,int state,String remark){
        JSONResult jsonResult = new JSONResult();
        try {
            realAuthService.audit(id,state,remark);
            jsonResult.setMsg("审核操作成功");
            jsonResult.setSuccess(true);

        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setMsg(e.getMessage());
            jsonResult.setSuccess(false);

        }
        return jsonResult;
    }

    @RequestMapping("realAuth")
    public String realAuth( Model model, @ModelAttribute("qo") RealAuthQueryObject qo){
        PageResult pageResult = realAuthService.query(qo);

        Realauth realauth = (Realauth) pageResult.getListData().get(1);
        System.out.println(realauth.getJsonString());

        model.addAttribute("pageResult",pageResult);
        return "realAuth/list";
    }
}
