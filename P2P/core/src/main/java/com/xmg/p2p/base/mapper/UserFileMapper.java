package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.UserFile;
import com.xmg.p2p.base.query.UserFileQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFileMapper {
    int insert(UserFile record);

    UserFile selectByPrimaryKey(Long id);

    List<UserFile> selectAll();

    int updateByPrimaryKey(UserFile record);

    List<UserFile> selectUserFile(@Param("loginInfoId") Long loginInfoId ,@Param("fileTypeIsNull") boolean fileTypeIsNull);

    void updateFileTypeId(@Param("id") Long id,@Param("fileType") Long fileType);

    int queryForCount(UserFileQueryObject qo);

    List<UserFile> query(UserFileQueryObject qo);
}