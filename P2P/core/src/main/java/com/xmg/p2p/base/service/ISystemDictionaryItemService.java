package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;

public interface ISystemDictionaryItemService {

    PageResult query(SystemDictionaryItemQueryObject systemDictionaryQueryObject);

    void update(SystemDictionaryItem systemDictionaryItem);
}
