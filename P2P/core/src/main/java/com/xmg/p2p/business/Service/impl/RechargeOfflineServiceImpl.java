package com.xmg.p2p.business.Service.impl;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.Service.IRechargeOfflineService;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.mapper.RechargeOfflineMapper;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RechargeOfflineServiceImpl implements IRechargeOfflineService {
    @Autowired
    RechargeOfflineMapper rechargeOfflineMapper;

    @Override
    public PageResult query(RechargeOfflineQueryObject qo) {
        int count = rechargeOfflineMapper.queryForCount(qo);
        if(count >0){
            List<RechargeOffline> list = rechargeOfflineMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }


    @Override
    public void apply(RechargeOffline recharge) {
        recharge.setApplier(UserContext.getCurrent());
        recharge.setApplyTime(new Date());
        recharge.setState(RechargeOffline.STATE_NORMAL);
        this.rechargeOfflineMapper.insert(recharge);
    }


}
