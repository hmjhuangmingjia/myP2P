package com.xmg.p2p.base.domain;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class SystemDictionary extends BaseDomain {
    private String sn;
    private String title;

    public String getJsonString(){
        Map<String,Object>json = new HashMap<>();
        json.put("sn",sn);
        json.put("title",title);
        json.put("id",super.getId());

        return JSONUtils.toJSONString(json);
    }
}
