package com.xmg.p2p.base.domain;




import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter@Setter
public class Iplog extends BaseDomain{
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAILED = 0;

    private String ip;
    private String username;
    private Date logintime;
    private int state;
    private int userType;//用户登录类型

    public String getstateDisplay(){
        return state == STATE_SUCCESS ? "登陆成功":"登陆失败";
    }

    public String getUserTypeDisplay(){
        return userType == Logininfo.USER_CLIENT ? "前台用户":"后台用户";
    }

}
