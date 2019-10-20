package com.xmg.p2p.base.service;

import com.xmg.p2p.base.query.IplogQueryObject;
import com.xmg.p2p.base.query.PageResult;

public interface IIplogService {

    PageResult query(IplogQueryObject qo);
}
