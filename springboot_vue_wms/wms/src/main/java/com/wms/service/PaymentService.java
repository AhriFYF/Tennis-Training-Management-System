package com.wms.service;

import com.wms.entity.User;
import com.wms.common.Result;
import java.math.BigDecimal;

public interface PaymentService {
    /**
     * 扣除用户余额
     * @param userId 用户ID
     * @param amount 扣除金额
     * @param description 扣费描述
     * @return 扣费结果
     */
    Result deductBalance(Integer userId, BigDecimal amount, String description);
    
    /**
     * 检查用户余额是否足够
     * @param userId 用户ID
     * @param amount 检查金额
     * @return 是否足够
     */
    boolean hasSufficientBalance(Integer userId, BigDecimal amount);
    
    /**
     * 获取用户余额
     * @param userId 用户ID
     * @return 用户余额
     */
    BigDecimal getUserBalance(Integer userId);
}