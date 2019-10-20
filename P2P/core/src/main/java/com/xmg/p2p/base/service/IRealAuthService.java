package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;

/**
 * 实名对象认证服务
 */
public interface IRealAuthService {
    Realauth get(Long id);

    void apply(Realauth realauth);

    PageResult query(RealAuthQueryObject qo);

    void audit(Long id, int state, String remark);
}
