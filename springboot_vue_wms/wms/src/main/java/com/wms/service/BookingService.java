package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.Booking;

import java.util.List;

public interface BookingService extends IService<Booking> {
    List<Booking> getStudentBookings(Integer studentId);
    boolean cancelBooking(Long bookingId, Integer studentId, String reason);
    int getMonthlyCancellationCount(Integer studentId);
}