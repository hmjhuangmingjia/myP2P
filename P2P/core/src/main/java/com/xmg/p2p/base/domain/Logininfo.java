package com.xmg.p2p.base.domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Logininfo extends BaseDomain {
    public static final int STATE_NORMAL = 0;//正常
    public static final int STATE_UNNORMAL = 1;//不正常

    public static final int USER_MANAGER = 0;//后台用户
    public static final int USER_CLIENT = 1;//前台用户

    private String username;

    private String password;

    private int state;

    private int usertype;




}