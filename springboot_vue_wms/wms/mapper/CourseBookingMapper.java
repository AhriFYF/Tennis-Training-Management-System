package com.wms.mapper;

import com.wms.entity.BookingStatus;
import com.wms.entity.CourseBooking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CourseBookingMapper {
    CourseBooking selectById(Long id);
    List<CourseBooking> selectByStudentId(Long studentId);
    List<CourseBooking> selectByCoachId(Long coachId);
    List<CourseBooking> selectByStatus(BookingStatus status);
    int insert(CourseBooking booking);
    int updateStatus(@Param("id") Long id, @Param("status") BookingStatus status);
    int updatePaymentStatus(@Param("id") Long id, @Param("isPaid") Boolean isPaid);
    int updateCancelInfo(@Param("id") Long id,
                         @Param("cancelInitiator") String cancelInitiator,
                         @Param("cancelConfirmed") Boolean cancelConfirmed,
                         @Param("cancelTime") LocalDateTime cancelTime);
    int delete(Long id);
}
