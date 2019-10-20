package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.service.IVeryfiCodeService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

@Service
public class VeryfiCodeServiceImpl implements IVeryfiCodeService {
    @Value("${sms.password}")
    String password;
    @Value("${sms.username}")
    String username;
    @Value("${sms.apikey}")
    String apiKey;
    @Value("${sms.url}")
    String url;

    @Override
    public void sendVeryfiCode(String phoneNumber) {
        //判断当前是否能够发信息
        VerifyCodeVO currentVerifyCode = UserContext.getCurrentVerifyCode();
        if (currentVerifyCode == null
                || DateUtil.secondsBetween(new Date(), currentVerifyCode.getLastSendTime()) > 90) {


            //生成验证码
            String code = UUID.randomUUID().toString().substring(0, 4);

            try {

                // 创建一个URL对象
                URL url = new URL(this.url);
                // 通过URL得到一个HTTPURLConnection连接对象;
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                // 拼接POST请求的内容
                StringBuilder content = new StringBuilder(100)
                        .append("username=").append(username)
                        .append("&password=").append(password)
                        .append("&apikey=").append(apiKey).append("&mobile=")
                        .append(phoneNumber).append("&content=")
                        .append("验证码是:").append(code).append(",请在5分钟内使用");
                // 发送POST请求,POST或者GET一定要大写
                conn.setRequestMethod("POST");
                // 设置POST请求是有请求体的
                conn.setDoOutput(true);
                // 写入POST请求体
                conn.getOutputStream().write(content.toString().getBytes());
                // 得到响应流(其实就已经发送了)
                String response = StreamUtils.copyToString(
                        conn.getInputStream(), Charset.forName("UTF-8"));

                if (response.startsWith("success:")) {
                    //发送验证码
                    System.out.println("手机号：" + phoneNumber + ",的验证码为：" + code);
                    //把验证码保存到session中
                    VerifyCodeVO verifyCodeVO = new VerifyCodeVO();
                    verifyCodeVO.setLastSendTime(new Date());
                    verifyCodeVO.setPhoneNumber(phoneNumber);
                    verifyCodeVO.setVerifyCode(code);
                    //把验证码放到session
                    UserContext.putVerifyCode(verifyCodeVO);
                } else {
                    // 发送失败
                    throw new RuntimeException();
                }


            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("短线发送失败");

            }
        } else {
            throw new RuntimeException("发送过于频繁");
        }
    }

    /**
     * 判断验证码是否正确
     * @param phoneNumber
     * @param verifyCode
     * @return
     */
    @Override
    public boolean verify(String phoneNumber, String verifyCode) {

        VerifyCodeVO currentVerifyCode = UserContext.getCurrentVerifyCode();
        if (phoneNumber.equals(currentVerifyCode.getPhoneNumber())
                && verifyCode.equals(currentVerifyCode.getVerifyCode())
                && DateUtil.secondsBetween(new Date(), currentVerifyCode.getLastSendTime()) <= BidConst.VERIFYCODE_VAILDATE_SECOND) {
            return true;
        }
        return false;
    }


}
