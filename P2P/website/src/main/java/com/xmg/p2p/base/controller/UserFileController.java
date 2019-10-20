package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.base.util.UploadUtil;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
public class UserFileController {
    @Autowired
    IUserFileService userFileService;

    @Autowired
    ISystemDictionaryService systemDictionaryService;

    @Autowired
    ServletContext servletContext;

    /**
     * 要先判断用户有没有分类的风控材料，如果有，则跳转到userFiles_commit.ftl
     *                             如果没有则跳转到userFiles.ftl
     * @param model
     * @param request
     * @return
     */
    @RequireLogin
    @RequestMapping("userFile")
    public String userFile(Model model, HttpServletRequest request){
        Long loginInfoId = UserContext.getCurrent().getId();
        //判断用户有没有分类的风控材料即fileTypeId是否为null
        boolean fileTypeIsNull = true;//
        List<UserFile> UnTypelist=userFileService.selectUserFile(loginInfoId,fileTypeIsNull);
        if(UnTypelist.size() <= 0){//如果为空，说明没有提交过材料
            fileTypeIsNull = false;
            List<UserFile> userFiles = userFileService.selectUserFile(loginInfoId, fileTypeIsNull);
            model.addAttribute("sessionid",request.getSession().getId());
            model.addAttribute("userFiles",userFiles);
            return "userFiles";
        }else {
            //没有设置风控分类的材料，则跳转到风控分类的图片
            //加载数据字典userFileType
            List<SystemDictionaryItem> fileTypes = systemDictionaryService.listByParentSn("userFileType");
            model.addAttribute("fileTypes",fileTypes);
            model.addAttribute("userFiles",UnTypelist);
            return "userFiles_commit";
        }
    }


    /**
     * 提交风控资料分类
     * 把fileType插入到 userfile的fileType_id
     * @param id userFile的id
     * @param fileType 风控分类id
     *
     */
    @RequireLogin
    @RequestMapping("userFile_selectType")
    @ResponseBody
    public void selectType(Long[] id,Long[] fileType){
        userFileService.selectType(id,fileType);
    }


    @RequestMapping("userFileUpload")
    @ResponseBody
    public void userFileUpload(MultipartFile file){
        // 先得到basepath
        String basePath = servletContext.getRealPath("/upload") ;
        System.out.println(basePath);
        String fileName = UploadUtil.upload(file, basePath );
        String image = "/upload/"+fileName;
        Logininfo applier = UserContext.getCurrent();
        userFileService.insert(applier,image);

    }
}
