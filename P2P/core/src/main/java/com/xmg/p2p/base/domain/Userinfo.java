package com.xmg.p2p.base.domain;

import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.MaskUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 用户对应的账户信息
 *
 * @author Administrator
 */
@Getter
@Setter
public class Userinfo extends BaseDomain {
    private int version;// 版本号
    private long bitState = 0;// 用户状态码
    private String realName;
    private String idNumber;
    private String phoneNumber;
    private String email;
    private int score;// 风控累计分数;
    private Long realAuthId;// 该用户对应的实名认证对象id

    private SystemDictionaryItem incomeGrade;// 收入
    private SystemDictionaryItem marriage;//
    private SystemDictionaryItem kidCount;//
    private SystemDictionaryItem educationBackground;//
    private SystemDictionaryItem houseCondition;//

    /**
     * 返回用户是否已经绑定手机
     *
     * @return
     */
    public boolean getIsBindPhone() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BIND_PHONE);
    }

    /**
     * 添加状态码
     * @param state
     */
    public void addState(Long state) {
        this.setBitState(BitStatesUtils.addState(this.getBitState(), state));
    }

    /**
     * 去掉状态码
     * @param state
     */
    public void removeState(Long state) {
        this.setBitState(BitStatesUtils.removeState(this.getBitState(),state));
    }

    /**
     * 返回用户是否已经填写了基本资料
     *
     * @return
     */
    public boolean getIsBasicInfo() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_BASIC_INFO);
    }

    /**
     * 是否已实名认证
     *
     * @return
     */
    public boolean getIsRealAuth() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_REAL_AUTH);
    }

    /**
     * 是否已视频认证
     *
     * @return
     */
    public boolean getIsVideoAuth() {
        return BitStatesUtils.hasState(this.bitState, BitStatesUtils.OP_VEDIO_AUTH);
    }


    /**
     * 返回用户是否绑定银行卡
     *
     * @return
     */
    public boolean getIsBindBank() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_BIND_BANKINFO);
    }

    /**
     * 返回用户是否有一个借款在处理流程当中
     *
     * @return
     */
    public boolean getHasBidRequestProcess() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_HAS_BIDREQUEST_PROCESS);
    }

    /**
     * 返回用户是否有一个提现申请在处理流程当中
     *
     * @return
     */
    public boolean getHasWithdrawProcess() {
        return BitStatesUtils.hasState(this.bitState,
                BitStatesUtils.OP_HAS_MONEYWITHDRAW_PROCESS);
    }



    public String getAnonymousRealName() {
        return MaskUtil.getAnonymousRealName(realName);
    }

    public String getAnonymousIdNumber() {
        return MaskUtil.getAnonymousIdNumber(idNumber);
    }


}
