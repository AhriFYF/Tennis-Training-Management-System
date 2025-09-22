package com.wms.controller;

import com.wms.entity.SystemMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/messages")
public class MessageController {
//    @Autowired
//    private SystemMessageMapper messageMapper;
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<SystemMessage>> getUserMessages(@PathVariable Long userId) {
//        List<SystemMessage> messages = messageMapper.selectByReceiverId(userId);
//        return ResponseEntity.ok(messages);
//    }
//
//    @PostMapping("/{id}/read")
//    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
//        messageMapper.updateReadStatus(id, true, LocalDateTime.now());
//        return ResponseEntity.ok().build();
//    }
}
