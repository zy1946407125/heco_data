package com.magic233.service;

import com.magic233.controller.HecoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ClassName: HecoFee
 * Description:
 * date: 2021/7/29 21:34
 * author: zouyuan
 */
@Component
public class HecoFee {

    @Autowired
    private HecoData1 hecoData1;

    public void start() {
//        6889398
        Scanner scanner = new Scanner(System.in);
        System.out.println("Fee: enter fee startBlock:");
        int num = scanner.nextInt();
        BigInteger startBlockHeight = BigInteger.valueOf(num);
//        BigInteger startBlockHeight = BigInteger.valueOf(6889398);
        System.out.println("Fee: startBlockHeight:  " + startBlockHeight);
        List<String> address = this.getPairs();
        BigInteger endBlockHeight = hecoData1.getBlockHeight(false);
        while (endBlockHeight.compareTo(BigInteger.valueOf(0)) == 0) {
            try {
                System.out.println("Fee: 获取区块高度出错了，更新web3j，休眠30秒");
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endBlockHeight = hecoData1.getBlockHeight(true);
        }
        while (startBlockHeight.compareTo(endBlockHeight) <= 0) {
            System.out.println("Fee: " + startBlockHeight);
            int start = hecoData1.start(startBlockHeight, address, false);
            while (start == 0) {
                try {
                    System.out.println("Fee: 获取交易出错了,更新web3j，休眠30秒");
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                start = hecoData1.start(startBlockHeight, address, true);
            }

            if (startBlockHeight.compareTo(endBlockHeight) == 0) {
                try {
                    System.out.println("Fee: 休眠30秒");
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                endBlockHeight = hecoData1.getBlockHeight(false);
                while (endBlockHeight.compareTo(BigInteger.valueOf(0)) == 0) {
                    try {
                        System.out.println("Fee: 获取区块高度出错了，更新web3j，休眠30秒");
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    endBlockHeight = hecoData1.getBlockHeight(true);
                }
                address = this.getPairs();
            }
            startBlockHeight = startBlockHeight.add(BigInteger.valueOf(1));
        }

    }

    public long getPairLength() {
        long pairLength = hecoData1.getPairLength(false);
        while (pairLength == -1) {
            try {
                System.out.println("Fee: 获取pair长度出错了，更新web3j，休眠30秒");
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pairLength = hecoData1.getPairLength(true);
        }
        return pairLength;
    }

    public String getPair(long i) {
        String pair = hecoData1.getPair(i, false);
        while ("".equals(pair)) {
            try {
                System.out.println("Fee: 获取pair地址出错了，更新web3j，休眠30秒");
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pair = hecoData1.getPair(i, true);
        }
        return pair;
    }

    public List<String> getPairs() {
        List<String> address = new ArrayList<>();
        long length = this.getPairLength();
        System.out.println("Fee: pairLength:" + length);
        for (long i = 0; i < length; i++) {
            String pair = this.getPair(i);
            address.add("0x" + pair.substring(26));
        }
        System.out.println("Fee: pairs：");
        for (int i = 0; i < address.size(); i++) {
            System.out.println(address.get(i));
        }
        return address;
    }
}
