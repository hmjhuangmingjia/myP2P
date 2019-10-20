package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryItemMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryItemQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {
    @Autowired
    SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public PageResult query(SystemDictionaryItemQueryObject qo) {
        int count = systemDictionaryItemMapper.queryForCount(qo);
        if (count > 0){
            List<SystemDictionaryItem> list = systemDictionaryItemMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
            return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void update(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.insert(systemDictionaryItem);
    }
}
