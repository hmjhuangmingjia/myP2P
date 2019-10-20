package com.xmg.p2p.base.service;

/**
 * 处理验证码的业务：
 * 发送验证码
 * 校验验证码
 *
 */
public interface IVeryfiCodeService {
    /**
     * 发送验证码
     * @param phoneNumber
     */
    void sendVeryfiCode(String phoneNumber);

    /**
     * 校验验证码
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    boolean verify(String phoneNumber,String verifyCode);
}
