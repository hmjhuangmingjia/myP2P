package com.xmg.mgrsite.base;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VedioAuthQueryObject;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVedioAuthService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 视频认证相关
 */
@Controller
public class VedioAuthController {

    @Autowired
    IVedioAuthService vedioAuthService;
    @Autowired
    IUserinfoService userinfoService;

    @RequestMapping("vedioAuth")
    public String vedioAuth(Model model, @ModelAttribute("qo") VedioAuthQueryObject qo){
        PageResult pageResult = vedioAuthService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "vedioAuth/list";

    }

    /**
     * 完成视频审核
     */
    @RequestMapping("vedioAuth_audit")
    @ResponseBody
    public JSONResult vedioAuthAudit(Long loginInfoValue,String remark,int state){
        try {
            this.vedioAuthService.audit(loginInfoValue,remark,state);
            return new JSONResult(true,"审核操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return new JSONResult(true,"审核操作失败");
        }

    }

    @RequestMapping("vedioAuth_autocomplate")
    @ResponseBody
    public List<Map> vedioAuthautocomplate(String keyword){
        return userinfoService.autoComplate(keyword);
    }
}
