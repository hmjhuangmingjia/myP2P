package com.xmg.p2p.business.query;

import com.xmg.p2p.base.query.BaseAuditQueryObject;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class RechargeOfflineQueryObject extends BaseAuditQueryObject {

    private String tradeCode;// 交易号
    private long bankInfoId = -1;
    private Long applierId;

    public String getTradeCode() {
        return StringUtils.hasLength(tradeCode) ? tradeCode : null;
    }

}
