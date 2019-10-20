package com.xmg.p2p.base.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter@Setter
public class SystemDictionaryItemQueryObject extends QueryObject {
    private String keyword;
    private int parentId;

    public String getKeyword(){
        return StringUtils.hasLength(keyword)?keyword:null;
    }
}
