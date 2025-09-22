package com.wms.controller;


import com.wms.entity.PaymentRecord;
import com.wms.entity.Student;
import com.wms.mapper.PaymentRecordMapper;
import com.wms.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private PaymentRecordMapper paymentRecordMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentMapper.selectById(id);
        return ResponseEntity.ok(student);
    }

    //TODO：充值待做
 //   @PostMapping("/{id}/recharge")//  public ResponseEntity<Void> rechargeAccount(@PathVariable Long id,
//                                                @RequestBody BigDecimal amount) {
//        studentMapper.updateAccountBalance(id, amount);
//
//        PaymentRecord record = new PaymentRecord();
//        record.setStudentId(id);
//        record.setAmount(amount);
//        record.setType(PaymentType.ONLINE);
//        record.setStatus("SUCCESS");
//        record.setPaymentTime(LocalDateTime.now());
//        paymentRecordMapper.insert(record);
//
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{id}/payment-records")
    public ResponseEntity<List<PaymentRecord>> getPaymentRecords(@PathVariable Long id) {
        List<PaymentRecord> records = paymentRecordMapper.selectByStudentId(id);
        return ResponseEntity.ok(records);
    }
}
