package com.magic233.service;

import com.magic233.entity.FeeResult;

import java.util.List;

/**
 * ClassName: Fee
 * Description:
 * date: 2021/7/26 15:03
 * author: zouyuan
 */
public interface Fee {
    public List<FeeResult> getFee(boolean isDay);

    Double getFees(boolean isDay);
}
