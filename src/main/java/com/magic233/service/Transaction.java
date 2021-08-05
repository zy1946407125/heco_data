package com.magic233.service;

import com.magic233.entity.TransactionResult;

import java.util.List;

/**
 * ClassName: Transaction
 * Description:
 * date: 2021/7/29 20:10
 * author: zouyuan
 */
public interface Transaction {
    List<TransactionResult> getTransaction(boolean isDay);

    Double getTransactions(boolean isDay);
}

