package com.magic233.service;

import com.magic233.entity.FeeResult;
import com.magic233.mapper.FeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TimeZone;

/**
 * ClassName: FeeImpl
 * Description:
 * date: 2021/7/29 20:55
 * author: zouyuan
 */
@Service
public class FeeImpl implements Fee {
    @Autowired
    private FeeMapper feeMapper;

    @Override
    public List<FeeResult> getFee(boolean isDay) {
        com.magic233.entity.Fee fee = new com.magic233.entity.Fee();
        if (isDay) {
            //查询24小时手续费
            long current = System.currentTimeMillis();
            long nowTime = current / 1000;
            long zero = (current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset()) / 1000;
            if (zero + 3600 * 12 < nowTime) {
                //过了今天中文12点，查询昨天中文十二点到今天中文12点
                long startTime = zero - 12 * 3600;
                long endTime = zero + 12 * 3600;
                fee.setStartTime(startTime);
                fee.setEndTime(endTime);
            } else {
                //没过今天中文12点，查询前天中文12点到昨天中文12点
                long startTime = zero - 12 * 3600 - 24 * 3600;
                long endTime = zero + 12 * 3600 - 24 * 3600;
                fee.setStartTime(startTime);
                fee.setEndTime(endTime);
            }
        }
        List<FeeResult> feeResults = feeMapper.selectFee(fee);
        return feeResults;
    }

    @Override
    public Double getFees(boolean isDay) {
        com.magic233.entity.Fee fee = new com.magic233.entity.Fee();
        if (isDay) {
            //查询24小时手续费
            long current = System.currentTimeMillis();
            long nowTime = current / 1000;
            long zero = (current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset()) / 1000;
            if (zero + 3600 * 12 < nowTime) {
                //过了今天中文12点，查询昨天中文十二点到今天中文12点
                long startTime = zero - 12 * 3600;
                long endTime = zero + 12 * 3600;
                fee.setStartTime(startTime);
                fee.setEndTime(endTime);
            } else {
                //没过今天中文12点，查询前天中文12点到昨天中文12点
                long startTime = zero - 12 * 3600 - 24 * 3600;
                long endTime = zero + 12 * 3600 - 24 * 3600;
                fee.setStartTime(startTime);
                fee.setEndTime(endTime);
            }
        }
        Double feeResults = feeMapper.selectFees(fee);
        return feeResults;
    }
}
