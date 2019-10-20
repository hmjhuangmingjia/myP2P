package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Iplog;
import com.xmg.p2p.base.mapper.IplogMapper;
import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IIplogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IIplogServiceImpl implements IIplogService {
    @Autowired
    IplogMapper iplogMapper;

    @Override
    public PageResult query(IplogQueryObject qo) {
        int count = iplogMapper.queryForCount(qo);

        if(count > 0){
            List<Iplog> list = iplogMapper.query(qo);
            PageResult pageResult = new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
            return pageResult;
        }


        return PageResult.empty(qo.getPageSize());
    }
}
