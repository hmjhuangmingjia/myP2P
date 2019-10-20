package com.xmg.p2p.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * 存放验证码相关内容,这个对象是放在session中的
 *
 * @author Administrator
 *
 */
@Getter@Setter
public class VerifyCodeVO {
    private String phoneNumber;
    private String VerifyCode;
    private Date lastSendTime;//最近成功发送时间
}
