package com.magic233.service;

import com.magic233.entity.Price;

import java.math.BigInteger;
import java.util.List;

/**
 * ClassName: hecoData
 * Description:
 * date: 2021/7/26 13:55
 * author: zouyuan
 */
public interface HecoData1 {

    public int start(BigInteger blockHeight, List<String> address, boolean updateWeb3j);

    public BigInteger getBlockHeight(boolean updateWeb3j);

    public long getPairLength(boolean updateWeb3j);

    public String getPair(long i, boolean updateWeb3j);

    public Price getPairPrice(String pair, boolean updateWeb3j);
}
