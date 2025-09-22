package com.wms.controller;

import com.wms.entity.PaymentRecord;
import com.wms.entity.Student;
import com.wms.entity.SystemLog;
import com.wms.mapper.PaymentRecordMapper;
import com.wms.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/logs")
public class LogController {
//    @Autowired
//    private SystemLogMapper logMapper;
//
//    @GetMapping
//    public ResponseEntity<List<SystemLog>> getLogs(
//            @RequestParam(required = false) Long userId,
//            @RequestParam(required = false) String actionType,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
//
//        List<SystemLog> logs = logMapper.selectByCondition(userId, actionType, startTime, endTime);
//        return ResponseEntity.ok(logs);
}
