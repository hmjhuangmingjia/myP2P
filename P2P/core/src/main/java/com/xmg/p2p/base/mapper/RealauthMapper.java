package com.xmg.p2p.base.mapper;

import com.xmg.p2p.base.domain.Realauth;
import com.xmg.p2p.base.query.RealAuthQueryObject;

import java.util.List;

public interface RealauthMapper {
    int insert(Realauth record);

    Realauth selectByPrimaryKey(Long id);


    int updateByPrimaryKey(Realauth record);

    Realauth selectAll (Long id);

    int queryForCount(RealAuthQueryObject qo);

    List<Realauth> query(RealAuthQueryObject qo);
}