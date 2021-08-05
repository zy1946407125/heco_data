package com.magic233.service;

import java.math.BigInteger;

/**
 * ClassName: HecoData
 * Description:
 * date: 2021/7/29 17:52
 * author: zouyuan
 */
public interface HecoData2 {
    public BigInteger getBlockHeight(boolean updateWeb3j);

    public int getTransaction(BigInteger blockHeight, boolean updateWeb3j);
}
