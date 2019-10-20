package com.xmg.p2p.business.Service.impl;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.Service.IPlatformBankInfoService;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.mapper.PlatformBankinfoMapper;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformBankInfoServiceImpl implements IPlatformBankInfoService {
    @Autowired
    PlatformBankinfoMapper platformBankinfoMapper;
    @Override
    public PageResult query(PlatformBankInfoQueryObject qo) {
        int count = platformBankinfoMapper.queryForCount(qo);
        if(count > 0){
            List<PlatformBankInfo> list = platformBankinfoMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void saveOrUpdate(PlatformBankInfo bankInfo) {
        if(bankInfo.getId() != null) {
            platformBankinfoMapper.updateByPrimaryKey(bankInfo);
        }else {
            platformBankinfoMapper.insert(bankInfo);
        }
    }

    @Override
    public List<PlatformBankInfo> listAll() {
        return platformBankinfoMapper.selectAll();
    }
}
