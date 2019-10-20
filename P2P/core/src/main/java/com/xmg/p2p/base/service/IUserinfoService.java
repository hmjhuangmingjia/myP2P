package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Userinfo;

import java.util.List;
import java.util.Map;

public interface IUserinfoService {

    void update (Userinfo userinfo);

    void add(Userinfo userinfo);

    Userinfo get(Long id);

    void bindPhone(String phoneNumber, String verifyCode);

    void updateBasicInfo(Userinfo userinfo);

    Userinfo getCurrent ();

    List<Map> autoComplate(String keyword);
}
