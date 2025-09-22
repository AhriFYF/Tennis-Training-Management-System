package com.wms.mapper;

import com.wms.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectById(Long id);
    User selectByUsername(String username);
    List<User> selectByCampusId(Long campusId);
    int insert(User user);
    int update(User user);
    int delete(Long id);
}
