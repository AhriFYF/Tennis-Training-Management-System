package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.student_users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentUsersMapper extends BaseMapper<student_users> {
    // 可以添加自定义方法
}