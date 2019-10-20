package com.xmg.p2p.business.Service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.PlatformBankInfo;
import com.xmg.p2p.business.query.PlatformBankInfoQueryObject;

import java.util.List;

public interface IPlatformBankInfoService {
    PageResult query (PlatformBankInfoQueryObject qo);

    void saveOrUpdate(PlatformBankInfo bankInfo);

    List<PlatformBankInfo> listAll();
}
