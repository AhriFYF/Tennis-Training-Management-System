package com.wms.controller;

import com.wms.entity.Coach;
import com.wms.mapper.CoachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {
    @Autowired
    private CoachMapper coachMapper;

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = coachMapper.selectAll();
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Coach>> searchCoaches(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) Long campusId) {

        List<Coach> coaches = coachMapper.selectByCondition(name, gender, minAge, maxAge, campusId);
        return ResponseEntity.ok(coaches);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approveCoach(@PathVariable Long id) {
        coachMapper.updateApprovalStatus(id, true);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectCoach(@PathVariable Long id) {
        coachMapper.updateApprovalStatus(id, false);
        return ResponseEntity.ok().build();
    }
}
