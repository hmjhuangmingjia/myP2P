package com.xmg.p2p.base.domain;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典明细
 *
 * @author Administrator
 *
 */
@Getter
@Setter
public class SystemDictionaryItem extends BaseDomain {
    private Long parentId;
    private String title;
    private int sequence;

    public String getJsonString(){
        Map<String,Object> json = new HashMap<>();
        json.put("title",title);
        json.put("id",super.getId());

        return JSONUtils.toJSONString(json);
    }

}
