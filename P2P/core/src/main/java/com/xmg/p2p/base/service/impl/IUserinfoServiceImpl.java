package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserinfoMapper;
import com.xmg.p2p.base.service.IVeryfiCodeService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IUserinfoServiceImpl implements IUserinfoService {
    @Autowired
    UserinfoMapper userinfoMapper;
    @Autowired
    IVeryfiCodeService veryfiCodeService;

    @Override
    public void update(Userinfo userinfo) {
        int ret = userinfoMapper.updateByPrimaryKey(userinfo);
        if (ret == 0) {
            throw new RuntimeException("乐观锁失败,Userinfo:" + userinfo.getId());
        }
    }

    @Override
    public void add(Userinfo userinfo) {
        int insert = userinfoMapper.insert(userinfo);
        if (insert == 0) {
            throw new RuntimeException("插入用户表数据表失败");
        }
    }

    @Override
    public Userinfo get(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void bindPhone(String phoneNumber, String verifyCode) {
            //如果用户没有绑定手机
            Userinfo userinfo = get(UserContext.getCurrent().getId());
            boolean flage = userinfo.getIsBindPhone();
            System.out.println(flage);
            if(!userinfo.getIsBindPhone()){

                //校验验证码
                boolean check = veryfiCodeService.verify(phoneNumber, verifyCode);
                if (check) {//验证码正确
                    //更新状态码
                    userinfo.addState(BitStatesUtils.OP_BIND_PHONE);

                    //更新手机号
                    userinfo.setPhoneNumber(phoneNumber);
                    //更新数据库数据
                    this.update(userinfo);
                } else {//验证码不通过
                    throw  new RuntimeException("验证码错误");
                }
            }

    }

    @Override
    public void updateBasicInfo(Userinfo userinfo) {
        //从session中取出用户id
        Userinfo db = this.get(UserContext.getCurrent().getId());

        /*
        不能只设置个id值
        userinfo.setId(id);
        userinfoMapper.updateByPrimaryKey(userinfo);
        */
        db.setMarriage(userinfo.getMarriage());
        db.setEducationBackground(userinfo.getEducationBackground());
        db.setIncomeGrade(userinfo.getIncomeGrade());
        db.setHouseCondition(userinfo.getHouseCondition());
        this.update(db);

        //设置状态码
        if(!db.getIsBasicInfo()){
            db.addState(BitStatesUtils.OP_BASIC_INFO);
        }

    }

    /**
     * 从session中获取Userinfo
     * @return
     */
    @Override
    public Userinfo getCurrent() {
        Userinfo userinfo = this.get(UserContext.getCurrent().getId());
        return userinfo;
    }

    @Override
    public List<Map> autoComplate(String keyword) {
        return userinfoMapper.autoComplate(keyword);
    }
}
