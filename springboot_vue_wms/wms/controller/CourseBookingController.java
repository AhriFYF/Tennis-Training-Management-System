package com.wms.controller;

import com.wms.entity.*;
import com.wms.mapper.CourseBookingMapper;
import com.wms.mapper.CourseScheduleMapper;
import com.wms.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/course-bookings")
public class CourseBookingController {
    @Autowired
    private CourseBookingMapper bookingMapper;
    @Autowired
    private CourseScheduleMapper scheduleMapper;
    @Autowired
    private StudentMapper studentMapper;

//    @PostMapping
//    public ResponseEntity<CourseBooking> createBooking(@RequestBody CourseBooking booking) {
//        // 检查学员账户余额是否足够
//        Student student = studentMapper.selectById(booking.getStudentId());
//        CourseSchedule schedule = scheduleMapper.selectById(booking.getScheduleId());
//        Coach coach = schedule.getCoach();
//
//        if (student.getAccountBalance().compareTo(coach.getHourlyRate()) < 0) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "账户余额不足");
//        }
//
//        booking.setStatus(BookingStatus.PENDING);
//        booking.setBookingTime(LocalDateTime.now());
//        booking.setFeeAmount(coach.getHourlyRate());
//        bookingMapper.insert(booking);
//
//        // 更新课程表状态
//        scheduleMapper.updateAvailability(schedule.getId(), false);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
//    }
//
//    @PostMapping("/{id}/confirm")
//    public ResponseEntity<Void> confirmBooking(@PathVariable Long id) {
//        CourseBooking booking = bookingMapper.selectById(id);
//        bookingMapper.updateStatus(id, BookingStatus.CONFIRMED);
//
//        // 扣除学员费用
//        Student student = studentMapper.selectById(booking.getStudentId());
//        studentMapper.updateAccountBalance(student.getId(), booking.getFeeAmount().negate());
//
//        // 更新支付状态
//        bookingMapper.updatePaymentStatus(id, true);
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/{id}/cancel")
//    public ResponseEntity<Void> cancelBooking(@PathVariable Long id,
//                                              @RequestParam String initiator) {
//        CourseBooking booking = bookingMapper.selectById(id);
//        bookingMapper.updateCancelInfo(id, initiator, false, LocalDateTime.now());
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/{id}/confirm-cancel")
//    public ResponseEntity<Void> confirmCancelBooking(@PathVariable Long id) {
//        CourseBooking booking = bookingMapper.selectById(id);
//        bookingMapper.updateStatus(id, BookingStatus.CANCELLED);
//        bookingMapper.updateCancelInfo(id, booking.getCancelInitiator(), true, LocalDateTime.now());
//
//        // 退还费用
//        if (booking.getIsPaid()) {
//            studentMapper.updateAccountBalance(booking.getStudentId(), booking.getFeeAmount());
//        }
//
//        // 更新课程表状态
//        scheduleMapper.updateAvailability(booking.getScheduleId(), true);
//
//        return ResponseEntity.ok().build();
//    }
}
