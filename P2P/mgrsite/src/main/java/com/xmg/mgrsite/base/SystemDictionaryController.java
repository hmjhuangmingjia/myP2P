package com.xmg.mgrsite.base;


import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import com.xmg.p2p.base.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;

    @RequestMapping("systemDictionary_list")
    public String systemDictionary (Model model, @ModelAttribute("qo") SystemDictionaryQueryObject qo){
        PageResult pageResult = systemDictionaryService.query(qo);
        model.addAttribute("pageResult",pageResult);
        return "systemdic/systemDictionary_list";
    }

    /**
     * 添加和修改数据字典
     * 通过有无id来判断是调用添加方法还是修改方法
     * @param systemDictionary
     * @return
     */
    @RequestMapping("systemDictionary_update")
    @ResponseBody
    public JSONResult updatesystemDictionary (SystemDictionary systemDictionary){
        JSONResult jsonResult = new JSONResult();
        try{

            systemDictionaryService.saveOrUpdate(systemDictionary);
            jsonResult.setMsg("保存成功");
            jsonResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg("保存失败");
        }


        return jsonResult;
    }

    @RequestMapping("systemDictionaryItem_list")
    public String systemDictionaryItem(Model model,@ModelAttribute("qo") SystemDictionaryItemQueryObject qo){
        PageResult pageResult = systemDictionaryItemService.query(qo);
        model.addAttribute("pageResult",pageResult);
        List<SystemDictionary> systemDictionaryGroups = systemDictionaryService.queryAll();
        model.addAttribute("systemDictionaryGroups",systemDictionaryGroups);
        return "systemdic/systemDictionaryItem_list";
    }

    @RequestMapping("systemDictionaryItem_update")
    @ResponseBody
    public JSONResult updateSystemDictionaryItem (SystemDictionaryItem systemDictionaryItem){
        JSONResult jsonResult = new JSONResult();
        try{
            systemDictionaryItemService.update(systemDictionaryItem);
            jsonResult.setMsg("保存成功");
            jsonResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMsg("保存失败");
        }
        return jsonResult;
    }
}
