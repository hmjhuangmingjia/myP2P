package com.xmg.p2p.business.Service.impl;

import com.xmg.p2p.base.domain.Account;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.business.Service.IBidrequestService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.mapper.BidrequestMapper;
import com.xmg.p2p.business.mapper.BidrequestaudithistoryMapper;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import com.xmg.p2p.business.util.CalculatetUtil;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BidrequestServiceImpl implements IBidrequestService {

    @Autowired
    private BidrequestMapper bidRequestMapper;

    @Autowired
    private IUserinfoService userinfoService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private BidrequestaudithistoryMapper bidrequestaudithistoryMapper;

    /**
     * 有乐观锁的，很可能修改失败，所以要抛出一个异常来回滚事务
     * 有事务的修改方法，凡是需要修改都要用这方法
     * @param bidRequest
     */
    @Override
    public void update(BidRequest bidRequest) {
        int count = bidRequestMapper.updateByPrimaryKey(bidRequest);
        if(count == 0){
            throw new RuntimeException("乐观锁失败   bidRequest:"+bidRequest.getId());
        }
    }

    /**
     * 判断用户是否有一个借款正在处理流程当中
     * @param id
     * @return
     */
    @Override
    public boolean canApplyBidRequeset(Long id) {
        Userinfo userinfo = userinfoService.get(id);
        userinfo.getHasBidRequestProcess();
        return !userinfo.getHasBidRequestProcess();
    }

    /**
     * 处理申请借款
     * @param br
     */
    @Override
    public void apply(BidRequest br) {
        Account account = this.accountService.getCurrent();
        // 首先满足最基本的申请条件;
        if (this.canApplyBidRequeset(UserContext.getCurrent().getId())//用户未处于借款处理中状态
                && br.getBidRequestAmount().compareTo(
                BidConst.SMALLEST_BIDREQUEST_AMOUNT) >= 0// 系统最小借款金额<=借款金额
                && br.getBidRequestAmount().compareTo(
                account.getRemainBorrowLimit()) <= 0// 借款金额<=剩余信用额度
                && br.getCurrentRate()
                .compareTo(BidConst.SMALLEST_CURRENT_RATE) >= 0// 5<=利息
                && br.getCurrentRate().compareTo(BidConst.MAX_CURRENT_RATE) <= 0// 利息<=20
                && br.getMinBidAmount().compareTo(BidConst.SMALLEST_BID_AMOUNT) >= 0// 最小投标金额>=系统最小投标金额
         ){
            // ==========进入借款申请
            // 1,创建一个新的BidRequest,设置相关参数;(为什么要设置一个新的BidRequest)
            BidRequest bidRequest = new BidRequest();
            bidRequest.setBidRequestAmount(br.getBidRequestAmount());
            bidRequest.setCurrentRate(br.getCurrentRate());
            bidRequest.setDescription(br.getDescription());
            bidRequest.setDisableDays(br.getDisableDays());
            bidRequest.setMinBidAmount(br.getMinBidAmount());
            bidRequest.setReturnType(br.getReturnType());
            bidRequest.setMonthes2Return(br.getMonthes2Return());
            bidRequest.setTitle(br.getTitle());
            // 2,设置相关值;
            bidRequest.setApplyTime(new Date());
            bidRequest
                    .setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
            bidRequest.setCreateUser(UserContext.getCurrent());
            bidRequest
                    .setTotalRewardAmount(CalculatetUtil.calTotalInterest(
                            bidRequest.getReturnType(),
                            bidRequest.getBidRequestAmount(),
                            bidRequest.getCurrentRate(),
                            bidRequest.getMonthes2Return()));
            // 3,保存;
            this.bidRequestMapper.insert(bidRequest);
            // 4,给借款人添加一个状态码
            Userinfo userinfo = this.userinfoService.getCurrent();
            userinfo.addState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            this.userinfoService.update(userinfo);
        }
    }

    /**
     * 分页查询
     * @param qo
     * @return
     */
    @Override
    public PageResult query(BidRequestQueryObject qo) {
        int count = this.bidRequestMapper.queryForCount(qo);
        if (count > 0) {
            List<BidRequest> list = this.bidRequestMapper.query(qo);
            return new PageResult(list, count, qo.getCurrentPage(),
                    qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }


    /**
     * 发标前审核
     * @param id
     * @param state
     * @param remark
     */
    @Override
    public void publishAudit(Long id, int state, String remark) {
        //查出bidrequest
        BidRequest br = bidRequestMapper.selectByPrimaryKey(id);
        //判断状态是否处于待发布状态
        if(br != null && br.getBidRequestState() == BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        //创建一个审核历史对象
        BidRequestAuditHistory history = new BidRequestAuditHistory();
        history.setApplier(br.getCreateUser());
        history.setApplyTime(br.getApplyTime());
        history.setAuditType(BidRequestAuditHistory.PUBLISH_AUDIT);//发表前审核状态
        history.setAuditor(UserContext.getCurrent());
        history.setAuditTime(new Date());
        history.setRemark(remark);
        history.setBidRequestId(br.getId());
        this.bidrequestaudithistoryMapper.insert(history);

        //审核通过或者不通过
        if(state == BidRequestAuditHistory.STATE_AUDIT){
            // 如果审核通过:修改标的状态,设置风控意见;
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_BIDDING);
            br.setDisableDate(DateUtils.addDays(new Date(),
                    br.getDisableDays()));
            br.setPublishTime(new Date());
            br.setNote(remark);
        }else {
            //如果审核失败:修改标的状态,用户去掉状态码;
            br.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_REFUSE);
            //去掉用户状态码
            Userinfo applier = this.userinfoService.get(br.getCreateUser().getId());
            applier.removeState(BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
            this.userinfoService.update(applier);
        }
        this.update(br);


    }

    @Override
    public BidRequest get(Long id) {
        return this.bidRequestMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BidRequestAuditHistory> listAuditHistoryByBidRequest(Long id) {
        return this.bidrequestaudithistoryMapper.listByBidRequest(id);
    }

    @Override
    public List<BidRequest> listIndex(int count) {
        BidRequestQueryObject qo = new BidRequestQueryObject();
        qo.setPageSize(count);
        qo.setCurrentPage(1);
        int [] bidRequestStates = {BidConst.BIDREQUEST_STATE_BIDDING,
                BidConst.BIDREQUEST_STATE_PAYING_BACK,
                BidConst.BIDREQUEST_STATE_COMPLETE_PAY_BACK
                                    };
        qo.setBidRequestStates(bidRequestStates);
        qo.setOrderType("ASC");
        qo.setOrderBy("bidRequestState");
        List<BidRequest> list= bidRequestMapper.query(qo);
        return list;
    }
}
