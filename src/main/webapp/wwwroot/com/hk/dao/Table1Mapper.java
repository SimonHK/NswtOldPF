package com.hk.dao;

import com.hk.domain.Table1;
import java.util.List;

public interface Table1Mapper {
    int deleteByPrimaryKey(Integer idnewtable);

    int insert(Table1 record);

    Table1 selectByPrimaryKey(Integer idnewtable);

    List<Table1> selectAll();

    int updateByPrimaryKey(Table1 record);
}