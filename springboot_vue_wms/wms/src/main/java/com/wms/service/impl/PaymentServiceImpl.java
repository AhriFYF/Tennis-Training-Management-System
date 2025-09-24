package com.wms.service.impl;

import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.PaymentService;
import com.wms.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    @Transactional
    public Result deductBalance(Integer userId, BigDecimal amount, String description) {
        // 检查用户是否存在
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        
        // 检查余额是否足够
        if (user.getBalance() == null || user.getBalance().compareTo(amount) < 0) {
            return Result.fail("余额不足，请充值");
        }
        
        // 扣除余额
        BigDecimal newBalance = user.getBalance().subtract(amount);
        user.setBalance(newBalance);
        
        // 更新用户余额
        int result = userMapper.updateById(user);
        if (result > 0) {
            return Result.suc("支付成功，扣除金额: " + amount + "元，余额: " + newBalance + "元");
        } else {
            return Result.fail("支付失败");
        }
    }
    
    @Override
    public boolean hasSufficientBalance(Integer userId, BigDecimal amount) {
        BigDecimal balance = getUserBalance(userId);
        return balance != null && balance.compareTo(amount) >= 0;
    }
    
    @Override
    public BigDecimal getUserBalance(Integer userId) {
        User user = userMapper.selectById(userId);
        return user != null ? user.getBalance() : BigDecimal.ZERO;
    }
}