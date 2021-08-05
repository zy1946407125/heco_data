package com.magic233.service;

import com.magic233.entity.TransactionResult;
import com.magic233.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimeZone;

/**
 * ClassName: TransactionImpl
 * Description:
 * date: 2021/7/29 20:11
 * author: zouyuan
 */
@Service
public class TransactionImpl implements Transaction {
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public List<TransactionResult> getTransaction(boolean isDay) {
        com.magic233.entity.Transaction transaction = new com.magic233.entity.Transaction();
        if (isDay) {
            //查询24小时手续费
            long current = System.currentTimeMillis();
            long nowTime = current / 1000;
            long zero = (current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset()) / 1000;
            if (zero + 3600 * 12 < nowTime) {
                //过了今天中文12点，查询昨天中文十二点到今天中文12点
                long startTime = zero - 12 * 3600;
                long endTime = zero + 12 * 3600;
                transaction.setStartTime(startTime);
                transaction.setEndTime(endTime);
            } else {
                //没过今天中文12点，查询前天中文12点到昨天中文12点
                long startTime = zero - 12 * 3600 - 24 * 3600;
                long endTime = zero + 12 * 3600 - 24 * 3600;
                transaction.setStartTime(startTime);
                transaction.setEndTime(endTime);
            }
        }
        List<TransactionResult> transactionResult = transactionMapper.selectTransaction(transaction);
        return transactionResult;
    }

    @Override
    public Double getTransactions(boolean isDay) {
        com.magic233.entity.Transaction transaction = new com.magic233.entity.Transaction();
        if (isDay) {
            //查询24小时手续费
            long current = System.currentTimeMillis();
            long nowTime = current / 1000;
            long zero = (current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset()) / 1000;
            if (zero + 3600 * 12 < nowTime) {
                //过了今天中文12点，查询昨天中文十二点到今天中文12点
                long startTime = zero - 12 * 3600;
                long endTime = zero + 12 * 3600;
                transaction.setStartTime(startTime);
                transaction.setEndTime(endTime);
            } else {
                //没过今天中文12点，查询前天中文12点到昨天中文12点
                long startTime = zero - 12 * 3600 - 24 * 3600;
                long endTime = zero + 12 * 3600 - 24 * 3600;
                transaction.setStartTime(startTime);
                transaction.setEndTime(endTime);
            }
        }
        Double transactionResult = transactionMapper.selectTransactions(transaction);
        return transactionResult;
    }
}
