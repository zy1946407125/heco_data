package com.magic233.mapper;

import com.magic233.entity.Transaction;
import com.magic233.entity.TransactionResult;

import java.util.List;

public interface TransactionMapper {
    int deleteByPrimaryKey(String hash);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String hash);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<TransactionResult> selectTransaction(Transaction transaction);

    Double selectTransactions(Transaction transaction);
}