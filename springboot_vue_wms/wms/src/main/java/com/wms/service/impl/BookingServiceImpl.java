package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.Booking;
import com.wms.mapper.BookingMapper;
import com.wms.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {

    @Override
    public List<Booking> getStudentBookings(Integer studentId) {
        LambdaQueryWrapper<Booking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Booking::getStudentId, studentId)
                .orderByDesc(Booking::getCourseStartTime);
        return this.list(queryWrapper);
    }

    @Override
    public boolean cancelBooking(Long bookingId, Integer studentId, String reason) {
        Booking booking = this.getById(bookingId);
        if (booking == null || !booking.getStudentId().equals(studentId)) {
            return false;
        }

        // 检查是否可以取消（提前24小时）
        LocalDateTime now = LocalDateTime.now();
        if (now.plusHours(24).isAfter(booking.getCourseStartTime())) {
            return false;
        }

        // 检查本月取消次数
        if (getMonthlyCancellationCount(studentId) >= 3) {
            return false;
        }

        // 更新预约状态
        booking.setStatus("已取消");
        booking.setCancellationReason(reason);
        booking.setCancelledBy("student");
        booking.setUpdatedTime(now);

        return this.updateById(booking);
    }

    @Override
    public int getMonthlyCancellationCount(Integer studentId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);

        LambdaQueryWrapper<Booking> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Booking::getStudentId, studentId)
                .eq(Booking::getStatus, "已取消")
                .ge(Booking::getUpdatedTime, startOfMonth);

        return this.count(queryWrapper);
    }
}