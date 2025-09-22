package com.wms.controller;

import com.wms.entity.CoachStudentRelation;
import com.wms.entity.RelationStatus;
import com.wms.mapper.CoachStudentRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/coach-student-relations")
public class CoachStudentRelationController {
    @Autowired
    private CoachStudentRelationMapper relationMapper;

//    @PostMapping
//    public ResponseEntity<CoachStudentRelation> createRelation(@RequestBody CoachStudentRelation relation) {
//        relation.setStatus(RelationStatus.PENDING);
//        relation.setApplyTime(LocalDateTime.now());
//        relationMapper.insert(relation);
//        return ResponseEntity.status(HttpStatus.CREATED).body(relation);
//    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approveRelation(@PathVariable Long id) {
        relationMapper.updateStatus(id, RelationStatus.APPROVED);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectRelation(@PathVariable Long id) {
        relationMapper.updateStatus(id, RelationStatus.REJECTED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CoachStudentRelation>> getRelationsByStudent(@PathVariable Long studentId) {
        List<CoachStudentRelation> relations = relationMapper.selectByStudentId(studentId);
        return ResponseEntity.ok(relations);
    }

    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<CoachStudentRelation>> getRelationsByCoach(@PathVariable Long coachId) {
        List<CoachStudentRelation> relations = relationMapper.selectByCoachId(coachId);
        return ResponseEntity.ok(relations);
    }
}
