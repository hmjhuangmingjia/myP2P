package com.xmg.p2p.base.util;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.vo.VerifyCodeVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserContext {

    public static final String USER_IN_SESSION = "logininfo";
    public static final String VERIFYCODE_IN_SESSION = "verifycode_in_session";


    /**
     * 反向获取request的方法
     *
     * @return
     */
    private static HttpSession getSession() {
        //要在要在web.xml中配置监听器org.springframework.web.context.request.RequestContextListener
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return session;
    }

    /**
     * 把用户信息放到session
     *
     * @param logininfo
     */

    public static void putCurrent(Logininfo logininfo) {
        //得到Session
        HttpSession session = UserContext.getSession();
        session.setAttribute(UserContext.USER_IN_SESSION, logininfo);

    }

    /**
     * 从session中获取用户信息的方法
     *
     * @return
     */
    public static Logininfo getCurrent() {
        HttpSession session = UserContext.getSession();
        Logininfo logininfo = (Logininfo) session.getAttribute(UserContext.USER_IN_SESSION);
        return logininfo;
    }

    /**
     * 把验证码放到session中
     *
     * @param verifyCodeVO
     */
    public static void putVerifyCode(VerifyCodeVO verifyCodeVO) {
        HttpSession session = UserContext.getSession();
        session.setAttribute(VERIFYCODE_IN_SESSION, verifyCodeVO);
    }

    public static VerifyCodeVO getCurrentVerifyCode() {
        HttpSession session = UserContext.getSession();
        return (VerifyCodeVO) session.getAttribute(VERIFYCODE_IN_SESSION);
    }

}
