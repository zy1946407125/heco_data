package com.magic233.mapper;

import com.magic233.entity.Fee;
import com.magic233.entity.FeeResult;

import java.util.List;

public interface FeeMapper {
    int deleteByPrimaryKey(String transactionhash);

    int insert(Fee record);

    int insertSelective(Fee record);

    Fee selectByPrimaryKey(String transactionhash);

    int updateByPrimaryKeySelective(Fee record);

    int updateByPrimaryKey(Fee record);

    List<FeeResult> selectFee(Fee fee);

    Double selectFees(Fee fee);
}