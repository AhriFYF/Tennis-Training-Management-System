package com.wms.mapper;

import com.wms.entity.PaymentRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentRecordMapper {
    PaymentRecord selectById(Long id);
    List<PaymentRecord> selectByStudentId(Long studentId);
    int insert(PaymentRecord record);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
