package com.xmg.p2p.business.Service;


import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.RechargeOffline;
import com.xmg.p2p.business.query.RechargeOfflineQueryObject;

public interface IRechargeOfflineService {
    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageResult query(RechargeOfflineQueryObject qo);

    /**
     * 线下充值
     * @param rechargeOffline
     */
    void apply (RechargeOffline rechargeOffline);
}
