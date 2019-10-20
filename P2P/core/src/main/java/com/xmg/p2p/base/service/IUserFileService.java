package com.xmg.p2p.base.service;

import com.xmg.p2p.base.domain.Logininfo;
import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.PageResult;
import com.xmg.p2p.base.query.UserFileQueryObject;

import java.util.List;

public interface IUserFileService {
    List<UserFile> selectUserFile(Long id, boolean fileTypeIsNull);

    void selectType(Long[] id, Long[] fileType);

    void insert(Logininfo applier, String image);

    PageResult displayUserFile (UserFileQueryObject qo);

    void audit(Long userFileId, int state, int score, String remark);

    List<UserFile> queryForList(UserFileQueryObject qo);
}
