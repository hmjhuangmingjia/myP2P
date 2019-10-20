package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.RealauthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RealAuthServiceImpl implements IRealAuthService {
    @Autowired
    RealauthMapper realauthMapper;
    @Autowired
    IUserinfoService userinfoService;


    @Override
    public Realauth get(Long id) {
        Realauth realauth = realauthMapper.selectByPrimaryKey(id);
        return realauth;
    }

    @Override
    public void apply(Realauth realauth) {
        //先判断用户是否有申请实名认证
        Userinfo current = this.userinfoService.getCurrent();
        if(!current.getIsRealAuth()&&current.getRealAuthId() == null){
            //补全参数
            realauth.setApplyTime(new Date());
            realauth.setApplier(UserContext.getCurrent());
            realauth.setState(Realauth.STATE_NORMAL);
            realauthMapper.insert(realauth);

            //在userinfo中关联Realauth,即设置设置realAuthId
            Userinfo userinfo = userinfoService.get(UserContext.getCurrent().getId());
            userinfo.setRealAuthId(realauth.getId());
            userinfoService.update(userinfo);

        }

    }

    @Override
    public PageResult query(RealAuthQueryObject qo) {
        int count = realauthMapper.queryForCount(qo);
        if(count > 0){
            List<Realauth> list = realauthMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }else {
            return PageResult.empty(qo.getPageSize());
        }

    }

    @Override
    public void audit(Long id, int state, String remark) {
        Realauth realauth = realauthMapper.selectAll(id);
            //补全参数
            if(realauth != null && realauth.getState() == Realauth.STATE_NORMAL){
                realauth.setAuditTime(new Date());
                realauth.setState(state);
                realauth.setRemark(remark);
                realauth.setAuditor(UserContext.getCurrent());
                realauthMapper.updateByPrimaryKey(realauth);

                //修改userinfo相关内容
                Userinfo applier = userinfoService.get(realauth.getApplier().getId());
                if(state == Realauth.STATE_REJECT){
                    applier.setRealAuthId(null);
                }else if(state == Realauth.STATE_AUDIT&&!applier.getIsRealAuth() ){
                   // applier.setRealAuthId(realauth.getId());不用设置了，之前申请审核的时候已经设置了
                    applier.addState(BitStatesUtils.OP_REAL_AUTH);
                }
                userinfoService.update(applier);
            }

    }
}
