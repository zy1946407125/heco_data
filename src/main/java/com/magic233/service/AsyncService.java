package com.magic233.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * ClassName: AsyncService
 * Description:
 * date: 2021/7/29 21:21
 * author: zouyuan
 */
@Service
public class AsyncService {
    @Autowired
    private HecoFee hecoFee;

    @Autowired
    private HecoTransaction hecoTransaction;

    // 指定使用beanname为doSomethingExecutor的线程池
    @Async("doSomethingExecutor")
    public void start1() {
        hecoFee.start();
    }


    // 指定使用beanname为doSomethingExecutor的线程池
    @Async("doSomethingExecutor")
    public void start2() {
        hecoTransaction.start();
    }
}
