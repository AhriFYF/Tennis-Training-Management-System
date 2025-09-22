package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

//    public User createUser(User user) {
//        // 密码加密
//        user.setPassword(encodePassword(user.getPassword()));
//        user.setCreateTime(LocalDateTime.now());
//        userMapper.insert(user);
//        return user;
//    }
//
//    public User updateUser(Long id, User user) {
//        user.setId(id);
//        user.setUpdateTime(LocalDateTime.now());
//        userMapper.update(user);
//        return user;
//    }
//
//    public void deleteUser(Long id) {
//        userMapper.delete(id);
//    }
//
//    private String encodePassword(String rawPassword) {
//        // 实际项目中应该使用BCrypt等加密算法
//        return DigestUtils.md5DigestAsHex(rawPassword.getBytes());
//    }
}
