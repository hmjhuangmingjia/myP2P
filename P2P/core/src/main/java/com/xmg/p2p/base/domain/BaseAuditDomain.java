package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础审核对象
 */
@Getter@Setter
public abstract class BaseAuditDomain extends BaseDomain{
    public static final int STATE_NORMAL =0;//正常
    public static final int STATE_AUDIT = 1;//审核通过
    public static final int STATE_REJECT = 2;//审核拒绝
    //实名认证审核相关
    protected int state;//状态
    protected String remark;//备注
    protected Logininfo auditor;//审核人
    protected Logininfo applier;//申请人
    protected Date auditTime;//申请时间
    protected Date applyTime;//审核时间

    public String getstateDisplay(){
        switch (state){
            case STATE_AUDIT:return "审核通过";
            case STATE_NORMAL:return "审核中";
            case STATE_REJECT:return  "审核失败";
            default:return "异常";
        }
    }
}
