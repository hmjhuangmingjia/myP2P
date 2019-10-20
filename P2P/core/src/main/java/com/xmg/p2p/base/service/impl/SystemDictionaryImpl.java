package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.SystemDictionary;
import com.xmg.p2p.base.domain.SystemDictionaryItem;
import com.xmg.p2p.base.mapper.SystemDictionaryMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.SystemDictionaryQueryObject;
import com.xmg.p2p.base.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryImpl implements ISystemDictionaryService {
    @Autowired
    SystemDictionaryMapper systemDictionaryMapper;

    @Override
    public PageResult query(SystemDictionaryQueryObject qo) {

        int count = systemDictionaryMapper.queryForCount(qo);
        if(count > 0){
            List<SystemDictionary> list = systemDictionaryMapper.query(qo);
            PageResult pageResult = new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
            return pageResult;
        }else {
            return PageResult.empty(qo.getPageSize());
        }
    }

    @Override
    public void saveOrUpdate(SystemDictionary systemDictionary) {
        if(systemDictionary.getId() != null){
            this.systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
        }else {
            this.systemDictionaryMapper.insert(systemDictionary);
        }
    }

    @Override
    public List<SystemDictionary> queryAll() {
        List<SystemDictionary> list = systemDictionaryMapper.selectAll();

        return list;
    }

    @Override
    public List<SystemDictionaryItem> listByParentSn(String sn) {
        List<SystemDictionaryItem> list =  systemDictionaryMapper.listByParentSn(sn);

        return list;
    }


}
