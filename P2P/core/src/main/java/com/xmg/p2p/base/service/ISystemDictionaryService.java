package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;

import java.util.List;

public interface ISystemDictionaryService {

    public PageResult query(SystemDictionaryQueryObject qo);


    void saveOrUpdate(SystemDictionary qo);

    List<SystemDictionary> queryAll();

    List<SystemDictionaryItem> listByParentSn(String sn);
}
