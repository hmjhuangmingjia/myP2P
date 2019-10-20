package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.domain.VedioAuth;
import com.xmg.p2p.base.mapper.RealauthMapper;
import com.xmg.p2p.base.mapper.VedioauthMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.RealAuthQueryObject;
import com.xmg.p2p.base.query.VedioAuthQueryObject;
import com.xmg.p2p.base.service.IRealAuthService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.service.IVedioAuthService;
import com.xmg.p2p.base.service.IVeryfiCodeService;
import com.xmg.p2p.base.util.BitStatesUtils;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

@Service
public class VedioAuthServiceImpl implements IVedioAuthService {
    @Autowired
    VedioauthMapper vedioauthMapper;
    @Autowired
    IUserinfoService userinfoService;


    @Override
    public VedioAuth get(Long id) {
        VedioAuth vedioAuth = vedioauthMapper.selectByPrimaryKey(id);
        return vedioAuth;
    }



    @Override
    public PageResult query(VedioAuthQueryObject qo) {
        int count = vedioauthMapper.queryForCount(qo);
        if(count > 0){
            List<VedioAuth> list = vedioauthMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }else {
            return PageResult.empty(qo.getPageSize());
        }

    }

    @Override
    public void audit(Long loginInfoValue, String remark, int state) {
        Userinfo userinfo = userinfoService.get(loginInfoValue);
        if(userinfo != null&&!userinfo.getIsVideoAuth()){
            //添加一个视频认证对象
            VedioAuth va = new VedioAuth();
            Logininfo applier = new Logininfo();

            applier.setId(loginInfoValue);
            va.setApplier(applier);
            va.setApplyTime(new Date());
            va.setAuditor(UserContext.getCurrent());
            va.setAuditTime(new Date());
            va.setRemark(remark);
            va.setState(state);
            vedioauthMapper.insert(va);

            if (state == VedioAuth.STATE_AUDIT) {
                // 如果状态审核通过,修改用户状态码
                userinfo.addState(BitStatesUtils.OP_VEDIO_AUTH);
                this.userinfoService.update(userinfo);
            }

        }
    }


}
