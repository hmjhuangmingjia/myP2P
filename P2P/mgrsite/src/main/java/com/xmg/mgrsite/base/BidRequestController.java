package com.xmg.mgrsite.base;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.JSONResult;
import com.xmg.p2p.business.Service.IBidrequestService;
import com.xmg.p2p.business.domain.BidRequest;
import com.xmg.p2p.business.domain.BidRequestAuditHistory;
import com.xmg.p2p.business.query.BidRequestQueryObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BidRequestController {
    @Autowired
    private IBidrequestService bidRequestService;

    @Autowired
    IUserinfoService userinfoService ;

    @Autowired
    IRealAuthService realAuthService;

    @Autowired
    IUserFileService userFileService;

    @RequestMapping("bidrequest_publishaudit_list")
    public String bidRequestPublishAuditList(@ModelAttribute("qo") BidRequestQueryObject qo, Model model) {
        //把状态改为待发行状态
        qo.setBidRequestState(BidConst.BIDREQUEST_STATE_PUBLISH_PENDING);
        model.addAttribute("pageResult", this.bidRequestService.query(qo));
        return "bidrequest/publish_audit";
    }

    @RequestMapping("bidrequest_publishaudit")
    @ResponseBody
    public JSONResult publishAudit(Long id, int state, String remark) {
        try {
            bidRequestService.publishAudit(id, state, remark);
            return new JSONResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(false, "审核操作失败");
        }
    }

    /**
     * 后台查看借款详情
     */
    @RequestMapping("borrow_info")
    public String borrowInfoDetail(Long id, Model model) {
        // bidRequest;
        BidRequest bidRequest = this.bidRequestService.get(id);
        model.addAttribute("bidRequest", bidRequest);
        // 借款人userInfo
        Userinfo userinfo = userinfoService.get(bidRequest.getCreateUser().getId());
        model.addAttribute("userInfo", userinfo);
        // audits:这个标的审核历史
        /**  applier的查询结果值为空，有问题*/
        List<BidRequestAuditHistory> bidRequestAuditHistories = this.bidRequestService.listAuditHistoryByBidRequest(id);
        model.addAttribute("audits",bidRequestAuditHistories
                );
        // realAuth:借款人实名认证信息
        model.addAttribute("realAuth",
                this.realAuthService.get(userinfo.getRealAuthId()));
        // userFiles:该借款人的风控资料信息
        UserFileQueryObject qo  = new UserFileQueryObject();
        qo.setApplierId(userinfo.getId());
        qo.setState(UserFile.STATE_AUDIT);
        qo.setPageSize(-1);
        model.addAttribute("userFiles", this.userFileService.queryForList(qo));
        return "bidrequest/borrow_info";
    }
}
