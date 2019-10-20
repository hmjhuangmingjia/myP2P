package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.VedioAuth;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.VedioAuthQueryObject;

/**
 * 实名对象认证服务
 */
public interface IVedioAuthService {
    VedioAuth get(Long id);


    PageResult query(VedioAuthQueryObject qo);

    void audit(Long loginInfoValue, String remark, int state);

}
