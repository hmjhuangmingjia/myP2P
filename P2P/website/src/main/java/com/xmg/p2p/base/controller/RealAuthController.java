package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
public class RealAuthController {
    @Autowired
    IRealAuthService realAuthService;
    @Autowired
    IUserinfoService userinfoService;
    @Autowired
    private ServletContext servletContext;

    /**
     * 保存realAuth
     * @return
     */
    @RequestMapping("realAuth_save")
    @ResponseBody
    public JSONResult realAuthSave(Realauth realauth){
        realAuthService.apply(realauth);
        return new JSONResult();
    }

    /**
     * 千万不要加requiredLogin
     *
     * @param file
     */

    @RequestMapping("realAuthUpload")
    @ResponseBody
    public String realAuthUpload(MultipartFile file){
        // 先得到basepath
        String basePath = servletContext.getRealPath("/upload");
        String fileName = UploadUtil.upload(file, basePath);
        return "/upload/" + fileName;
    }


    @RequireLogin
    @RequestMapping("realAuth")
    public String realAuth(Model model){
        //得到当前用户对象Userinfo
        Userinfo current = userinfoService.getCurrent();
        if(current.getIsRealAuth()){
            //如果已经实名认证，根据userinfo上的realAuthId得到实名认证对象，并放在model
            model.addAttribute("realAuth",realAuthService.get(current.getRealAuthId()));
            //auditing = false
            model.addAttribute("auditing",false);

            return "realAuth_result";
        }else {
            //如果用户没有实名认证：1：用户提交了审核，没通过有realAuth，auditing = true；
            //                  2：用户没提交过审核，跳转到realAuth
            if(current.getRealAuthId() != null){
                model.addAttribute("auditing",true);
                return "realAuth_result";
            }else {
                return "realAuth";
            }
        }




    }
}
