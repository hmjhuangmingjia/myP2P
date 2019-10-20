package com.xmg.p2p.business.domain;

import com.alibaba.fastjson.JSONObject;
import com.xmg.p2p.base.domain.BaseAuditDomain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class RechargeOffline extends BaseAuditDomain {

    private String tradeCode;// 交易号
    private Date tradeTime;// 充值时间
    private BigDecimal amount;// 充值金额
    private String note;// 充值说明
    private PlatformBankInfo bankInfo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getJsonString() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", this.getId());
        json.put("username", this.applier.getUsername());
        json.put("tradeCode", tradeCode);
        json.put("amount", amount);
        json.put("tradeTime", tradeTime);
        return JSONObject.toJSONString(json);
    }
}
