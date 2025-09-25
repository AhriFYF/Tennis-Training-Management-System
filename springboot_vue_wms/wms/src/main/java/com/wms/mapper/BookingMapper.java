package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.Booking;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {
    // 可以添加自定义查询方法
    // 例如：根据学生ID查询预约记录
    // List<Booking> selectByStudentId(Integer studentId);
}