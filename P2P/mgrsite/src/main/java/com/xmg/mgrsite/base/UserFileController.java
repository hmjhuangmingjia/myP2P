package com.xmg.mgrsite.base;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserFileController {
    @Autowired
    IUserFileService userFileService;

    /**
     * 进入认证材料审核页面
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("userFileAuth")
    public String userFile (@ModelAttribute("qo") UserFileQueryObject qo , Model model){
        PageResult pageResult = userFileService.displayUserFile(qo);
        model.addAttribute("pageResult",pageResult);
        return "userFileAuth/list";

    }

    @RequestMapping("userFile_audit")
    @ResponseBody
    public void audit(@RequestParam(value = "id") Long userFileId , int state , int score , String remark){
        userFileService.audit(userFileId,state,score,remark);
    }
}
