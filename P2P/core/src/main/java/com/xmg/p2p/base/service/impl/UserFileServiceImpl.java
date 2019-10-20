package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.domain.Userinfo;
import com.xmg.p2p.base.mapper.UserFileMapper;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;
import com.xmg.p2p.base.service.ILogininfoService;
import com.xmg.p2p.base.service.IUserFileService;
import com.xmg.p2p.base.service.IUserinfoService;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserFileServiceImpl implements IUserFileService {

    @Autowired
    UserFileMapper userFileMapper;
    @Autowired
    IUserinfoService userinfoService;

    /**
     * 查询用户风控提交的资料
     *
     * @param logininfoId
     * @param fileTypeIsNull
     * @return
     */
    @Override
    public List<UserFile> selectUserFile(Long logininfoId, boolean fileTypeIsNull) {
        //按用户id查询
        List<UserFile> list = userFileMapper.selectUserFile(logininfoId, fileTypeIsNull);
        return list;
    }


    /**
     * 提交风控资料分类
     * 把fileType插入到 userfile的fileType_id
     * @param ids userFile的id
     * @param fileTypes 风控分类id
     *
     */
    @Override
    public void selectType(Long[] ids, Long[] fileTypes) {
        if(ids.length > 0 && fileTypes.length > 0 ){

            for(int i = 0;i < ids.length;i++){
                userFileMapper.updateFileTypeId(ids[i],fileTypes[i]);
            }
        }
    }

    /**
     * 插入数据
     * @param applier
     * @param image
     */
    @Override
    public void insert(Logininfo applier, String image) {
        UserFile userFile = new UserFile();
        userFile.setImage(image);
        userFile.setApplier(applier);
        userFile.setApplyTime(new Date());
        userFile.setState(UserFile.STATE_NORMAL);
        userFileMapper.insert(userFile);
    }

    /**
     * 显示风控材料审核列表
     */
    @Override
    public PageResult displayUserFile (UserFileQueryObject qo){
        int count = userFileMapper.queryForCount(qo);
        if(count > 0 ){
            List<UserFile> list =  userFileMapper.query(qo);
            return new PageResult(list,count,qo.getCurrentPage(),qo.getPageSize());
        }
        return PageResult.empty(qo.getPageSize());
    }

    @Override
    public void audit(Long userFileId, int state, int score, String remark) {
        UserFile userFile = userFileMapper.selectByPrimaryKey(userFileId);
        //判断是否是审核中状态
        if(userFile.getState() == UserFile.STATE_NORMAL){
            userFile.setState(state);
            userFile.setScore(score);
            userFile.setRemark(remark);
            userFile.setAuditTime(new Date());
            userFile.setAuditor(UserContext.getCurrent());
            userFileMapper.updateByPrimaryKey(userFile);

            //更新用户材料得分
            Userinfo applier = userinfoService.get(userFile.getApplier().getId());
            int newScore = applier.getScore() + score;
            applier.setScore(newScore);
            userinfoService.update(applier);
        }
    }

    @Override
    public List<UserFile> queryForList(UserFileQueryObject qo) {
        return userFileMapper.query(qo);
    }
}

