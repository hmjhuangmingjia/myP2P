package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.base.domain.BaseDomain;
import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.business.util.DecimalFormatUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xmg.p2p.base.util.BidConst.*;

@Getter
@Setter
public class BidRequest extends BaseDomain {
    private int version;  //乐观锁
    private int returnType; //还款方式
    private int bidRequestType;//借款类型，在我们项目中，就是信用借款
    private int bidRequestState;//标的的状态
    private BigDecimal bidRequestAmount;//这个借款的金额
    private BigDecimal currentRate;//这个借款的利率
    private BigDecimal minBidAmount;//这个借款允许的最小的投标金额，默认是50；
    private int monthes2Return;//借款期限，就是这个借款的还款时间，单位是月，一般可供1~18选择
    private int bidCount;//这个借款现在已经有多少次投标（这个数据是用于标的监控看的）
    private BigDecimal totalRewardAmount;//总回报金额
    private BigDecimal currentSum = BidConst.ZERO;//当前已经投了多少钱了；
    private String title;//借款标题
    private String description;//借款描述
    private String note;//风控评审意见，这个意见是需要在标的里面展示给用户看的，这个意见也是发标前审核的审核意见；
    private Date disableDate;//招标到期时间，这个是根据前台选择的招标时间和发标前审核的时间来决定的，超过这个时间，如果标的还没有投满，就需要进行流标操作；
    private int disableDays;//招标天数，这个就是前台填写的招标天数；
    private Logininfo createUser;//借款人
    private List<Bid> bids;//这个标的的投标记录；
    private Date applyTime;//申请时间
    private Date publishTime;//发布时间，这个时间在我们项目中就是发标前审核的时间，在实际项目中，可能有些标是可以设定一个时间，定时发标的；

    /**
     * 还需多少钱
     * @return
     */
    public BigDecimal getRemainAmount(){
        return DecimalFormatUtil.formatBigDecimal(
                bidRequestAmount.subtract(currentSum), BidConst.DISPLAY_SCALE);
    }
    /**
     * 计算当前投标进度
     */
    public BigDecimal getPersent() {
        return currentSum.divide(bidRequestAmount, BidConst.DISPLAY_SCALE,
                RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }


    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", super.getId());
        json.put("username", this.createUser.getUsername());
        json.put("title", title);
        json.put("bidRequestAmount", bidRequestAmount);
        json.put("currentRate", currentRate);
        json.put("monthes2Return", monthes2Return);
        json.put("returnType", getReturnTypeDisplay());
        json.put("totalRewardAmount", totalRewardAmount);
        return JSONObject.toJSONString(json);
    }

    public String getReturnTypeDisplay() {
        return returnType == BidConst.RETURN_TYPE_MONTH_INTEREST ? "按月到期"
                : "等额本息";
    }

    public String getBidRequestStateDisplay() {
        switch (this.bidRequestState) {
            case BIDREQUEST_STATE_PUBLISH_PENDING:
                return "待发布";
            case BIDREQUEST_STATE_BIDDING:
                return "招标中";
            case BIDREQUEST_STATE_UNDO:
                return "已撤销";
            case BIDREQUEST_STATE_BIDDING_OVERDUE:
                return "流标";
            case BIDREQUEST_STATE_APPROVE_PENDING_1:
                return "满标一审";
            case BIDREQUEST_STATE_APPROVE_PENDING_2:
                return "满标二审";
            case BIDREQUEST_STATE_REJECTED:
                return "满标审核被拒";
            case BIDREQUEST_STATE_PAYING_BACK:
                return "还款中";
            case BIDREQUEST_STATE_COMPLETE_PAY_BACK:
                return "完成";
            case BIDREQUEST_STATE_PAY_BACK_OVERDUE:
                return "逾期";
            case BIDREQUEST_STATE_PUBLISH_REFUSE:
                return "发标拒绝";
            default:
                return "";
        }
    }


}