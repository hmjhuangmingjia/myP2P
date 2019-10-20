package com.xmg.p2p.base.domain;


import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserFile extends BaseAuditDomain {

    private int score;

    private String image;

    private SystemDictionary fileType;

    public String getJsonString (){
        Map<String,Object> json = new HashMap<>();
        json.put("id" , super.getId());
        json.put("applier",super.getApplier().getUsername());
        json.put("fileType",this.getFileType().getTitle());
        json.put("image",this.getImage());
        return JSONUtils.toJSONString(json);
    }


}
