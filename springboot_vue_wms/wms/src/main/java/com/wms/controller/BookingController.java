package com.wms.controller;

import com.wms.common.Result;
import com.wms.entity.Booking;
import com.wms.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // 获取学生的所有预约
    @GetMapping("/student/{studentId}")
    public Result getStudentBookings(@PathVariable Integer studentId) {
        List<Booking> bookings = bookingService.getStudentBookings(studentId);
        return Result.suc(bookings);
    }

    // 取消预约
    @PostMapping("/cancel/{bookingId}")
    public Result cancelBooking(@PathVariable Long bookingId,
                                @RequestBody Map<String, String> requestData,
                                HttpServletRequest request) {
        // 从session获取用户ID
        Integer studentId = (Integer) request.getSession().getAttribute("userId");
        if (studentId == null) {
            return Result.fail("未登录或会话已过期");
        }

        String reason = requestData.get("reason");
        boolean success = bookingService.cancelBooking(bookingId, studentId, reason);

        if (success) {
            return Result.suc("取消预约成功");
        } else {
            return Result.fail("取消预约失败，请检查是否符合取消条件");
        }
    }

    // 获取本月取消次数
    @GetMapping("/cancellationCount")
    public Result getCancellationCount(HttpServletRequest request) {
        // 从session获取用户ID
        Integer studentId = (Integer) request.getSession().getAttribute("userId");
        if (studentId == null) {
            return Result.fail("未登录或会话已过期");
        }

        int count = bookingService.getMonthlyCancellationCount(studentId);
        Map<String, Object> result = new HashMap<>();
        result.put("count", count);
        result.put("remaining", 3 - count);

        return Result.suc(result);
    }
}