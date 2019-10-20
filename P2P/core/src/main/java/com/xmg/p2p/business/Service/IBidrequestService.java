package com.xmg.p2p.business.Service;

import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.query.BidRequestQueryObject;

import java.util.List;

public interface IBidrequestService {

    void update(BidRequest bidRequest);

    /**
     * 判断用户是否处于申请借款流程中
     * @param id
     * @return
     */
    boolean canApplyBidRequeset(Long id);

    /**
     * 处理申请借款
     * @param br
     */
    void apply(BidRequest br);

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageResult query(BidRequestQueryObject qo);

    /**
     * 发标前审核
     * @param id
     * @param state
     * @param remark
     */
    void publishAudit(Long id, int state, String remark);

    /**
     * 通过id获得BidRequest
     * @param id
     * @return
     */
    BidRequest get(Long id);

    /**
     * 查询标的历史
     * @param id
     * @return
     */
    List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id);

    /**
     * 查询首页数据
     * @param count 显示的数量
     * @return
     */
    List<BidRequest> listIndex(int count);
}
