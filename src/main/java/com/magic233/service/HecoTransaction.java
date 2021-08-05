package com.magic233.service;

import com.magic233.controller.HecoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * ClassName: HecoTransaction
 * Description:
 * date: 2021/7/29 22:02
 * author: zouyuan
 */
@Component
public class HecoTransaction {

    @Autowired
    private HecoData2 hecoData2;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transaction: enter transaction startBlock:");
        int num = scanner.nextInt();
        BigInteger startBlockHeight = BigInteger.valueOf(num);
        System.out.println("Transaction: startBlockHeight:  " + startBlockHeight);

        BigInteger endBlockHeight = hecoData2.getBlockHeight(false);
        while (endBlockHeight.compareTo(BigInteger.valueOf(0)) == 0) {
            try {
                System.out.println("Transaction: 获取区块高度出错了，更新web3j，休眠30秒");
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endBlockHeight = hecoData2.getBlockHeight(true);
        }

        while (startBlockHeight.compareTo(endBlockHeight) <= 0) {
            System.out.println("Transaction: " + startBlockHeight);
            int re = hecoData2.getTransaction(startBlockHeight, false);
            while (re == 0) {
                try {
                    System.out.println("Transaction: 获取block信息出错了，更新web3j，休眠30秒");
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                re = hecoData2.getTransaction(startBlockHeight, true);
            }
            if (startBlockHeight.compareTo(endBlockHeight) == 0) {
                try {
                    System.out.println("Transaction: 休眠30秒");
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Transaction: 休眠结束");
                endBlockHeight = hecoData2.getBlockHeight(false);
                while (endBlockHeight.compareTo(BigInteger.valueOf(0)) == 0) {
                    try {
                        System.out.println("Transaction: 获取区块高度出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    endBlockHeight = hecoData2.getBlockHeight(true);
                }
            }
            startBlockHeight = startBlockHeight.add(BigInteger.valueOf(1));
        }
    }
}
