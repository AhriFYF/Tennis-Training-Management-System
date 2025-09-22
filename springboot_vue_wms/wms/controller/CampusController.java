package com.wms.controller;

import com.wms.entity.Campus;
import com.wms.mapper.CampusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campuses")
public class CampusController {
    @Autowired
    private CampusMapper campusMapper;

    @GetMapping
    public ResponseEntity<List<Campus>> getAllCampuses() {
        List<Campus> campuses = campusMapper.selectAll();
        return ResponseEntity.ok(campuses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campus> getCampusById(@PathVariable Long id) {
        Campus campus = campusMapper.selectById(id);
        return ResponseEntity.ok(campus);
    }

    @PostMapping
    public ResponseEntity<Campus> createCampus(@RequestBody Campus campus) {
        campusMapper.insert(campus);
        return ResponseEntity.status(HttpStatus.CREATED).body(campus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campus> updateCampus(@PathVariable Long id, @RequestBody Campus campus) {
        campus.setId(id);
        campusMapper.update(campus);
        return ResponseEntity.ok(campus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        campusMapper.delete(id);
        return ResponseEntity.noContent().build();
    }
}
