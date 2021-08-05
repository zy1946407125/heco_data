package com.magic233.controller;

import com.magic233.entity.FeeResult;
import com.magic233.entity.TransactionResult;
import com.magic233.service.AsyncService;
import com.magic233.service.Fee;
import com.magic233.service.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * ClassName: HecoData
 * Description:
 * date: 2021/7/29 21:17
 * author: zouyuan
 */

@RestController
public class HecoData {
    @Autowired
    private AsyncService asyncService;

    @Autowired
    private Fee fee;

    @Autowired
    private Transaction transaction;

    @PostConstruct
    public void something() {
        asyncService.start1();
        asyncService.start2();
    }

    @RequestMapping("/getFee")
    public Object getFee(boolean isDay) {
        List<FeeResult> feeResult = this.fee.getFee(isDay);
        return feeResult;
    }

    @RequestMapping("/getFees")
    public Object getFees(boolean isDay) {
        Double feeResult = this.fee.getFees(isDay);
        if(feeResult == null){
            return 0;
        }
        return feeResult;
    }

    @RequestMapping("/getTransaction")
    public Object getTransaction(boolean isDay) {
        List<TransactionResult> transactionResult = this.transaction.getTransaction(isDay);
        return transactionResult;
    }

    @RequestMapping("/getTransactions")
    public Object getTransactions(boolean isDay) {
        Double transactionResult = this.transaction.getTransactions(isDay);
        if(transactionResult == null){
            return 0;
        }
        return transactionResult;
    }


}
