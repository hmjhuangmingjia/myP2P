package com.xmg.p2p.business.mapper;

import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import java.util.List;

public interface BidrequestaudithistoryMapper {

    int insert(BidRequestAuditHistory record);

    BidRequestAuditHistory selectByPrimaryKey(Long id);


    List<BidRequestAuditHistory> listByBidRequest(Long id);
}