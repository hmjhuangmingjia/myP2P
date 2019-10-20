package com.xmg.p2p.base.controller;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.IAccountService;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.util.RequireLogin;
import com.xmg.p2p.business.Service.IBidrequestService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.business.domain.BidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.management.LockInfo;

@Controller
public class BorrowController {
    @Autowired
    IAccountService accountService;
    @Autowired
    IUserinfoService userinfoService;
    @Autowired
    IBidrequestService bidrequestService;
    @Autowired
    IRealAuthService realAuthService;
    @Autowired
    IUserFileService userFileService;

    /**
     * 未登录就重定向到静态页面
     * 已登陆就转发到动态页面
     *
     * @return
     */
    @RequestMapping("borrow")
    public String borrow(Model model) {
        //判断登陆情况
        Logininfo current = UserContext.getCurrent();
        if (current == null) {
            //未登陆就重定向到
            return "redirect:/borrow.html";
        } else {
            //把值保存到model发过去
            model.addAttribute("userinfo", userinfoService.get(current.getId()));
            model.addAttribute("account", accountService.get(current.getId()));
            model.addAttribute("creditBorrowScore", BidConst.BASE_BORROW_SCORE);
            return "borrow";
        }
    }

    /**
     * 跳转到借款页面
     *
     * @param model
     * @return
     */
    @RequireLogin
    @RequestMapping("borrowInfo")
    public String borrowInfo(Model model) {
        Long id = UserContext.getCurrent().getId();
        if (bidrequestService.canApplyBidRequeset(id)) {
            model.addAttribute("minBidRequestAmount", BidConst.SMALLEST_BID_AMOUNT);// 最小借款金额
            model.addAttribute("minBidAmount", BidConst.SMALLEST_BID_AMOUNT);// 系统规定的最小投标金额
            model.addAttribute("account", this.accountService.getCurrent());
            return "borrow_apply";
        } else {
            return "borrow_apply_result";
        }
    }

    @RequireLogin
    @RequestMapping("borrow_apply")
    public String apply(BidRequest bidRequest, Object createuserId) {
        bidrequestService.apply(bidRequest);

        return "borrow_apply_result";
    }

    @RequestMapping("borrow_info")
    public String borrow_Info(Model model, @RequestParam("id") Long bidRequestId) {
        //bidRequest
        BidRequest bidRequest = bidrequestService.get(bidRequestId);
        if (bidRequest != null) {
            //userinfo
            Userinfo applier = userinfoService.getCurrent();
            // realAuth:借款人实名认证信息
            Realauth realAuth = realAuthService.get(applier.getRealAuthId());
            model.addAttribute("realAuth", realAuth);
            // userFiles:借款人风控材料
            UserFileQueryObject qo = new UserFileQueryObject();
            qo.setApplierId(applier.getId());
            qo.setPageSize(-1);
            qo.setCurrentPage(1);
            model.addAttribute("userFiles",
                    this.userFileService.queryForList(qo));
            model.addAttribute("bidRequest", bidRequest);
            model.addAttribute("userInfo", applier);

            if (UserContext.getCurrent() != null) {
                // self:当前用户是否是借款人自己
                if(UserContext.getCurrent().getId().equals(applier.getId())){
                    model.addAttribute("self", true);
                }else {
                    // account
                    model.addAttribute("self", false);
                    model.addAttribute("account",
                            this.accountService.getCurrent());
                }
            }
        }else {
            model.addAttribute("self", false);
        }
        return "borrow_info";
    }
}
