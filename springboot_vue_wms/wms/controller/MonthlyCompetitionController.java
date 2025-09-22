package com.wms.controller;
import com.wms.entity.MonthlyCompetition;
import com.wms.mapper.MonthlyCompetitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class MonthlyCompetitionController {
//    @Autowired
//    private MonthlyCompetitionMapper competitionMapper;
//    @Autowired
//    private CompetitionRegistrationMapper registrationMapper;
//
//    @GetMapping
//    public ResponseEntity<List<MonthlyCompetition>> getCompetitions(
//            @RequestParam(required = false) Long campusId,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//
//        List<MonthlyCompetition> competitions;
//        if (campusId != null) {
//            competitions = competitionMapper.selectByCampusId(campusId);
//        } else if (startDate != null && endDate != null) {
//            competitions = competitionMapper.selectByDateRange(startDate, endDate);
//        } else {
//            competitions = competitionMapper.selectAll();
//        }
//
//        return ResponseEntity.ok(competitions);
//    }
//
//    @PostMapping("/{competitionId}/register")
//    public ResponseEntity<CompetitionRegistration> registerCompetition(
//            @PathVariable Long competitionId,
//            @RequestParam Long studentId,
//            @RequestParam CompetitionGroup group) {
//
//        CompetitionRegistration registration = new CompetitionRegistration();
//        registration.setCompetitionId(competitionId);
//        registration.setStudentId(studentId);
//        registration.setGroup(group);
//        registration.setRegisterTime(LocalDateTime.now());
//        registration.setIsPaid(false);
//
//        registrationMapper.insert(registration);
//        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
//    }
//
//    @PostMapping("/registrations/{id}/pay")
//    public ResponseEntity<Void> payRegistrationFee(@PathVariable Long id) {
//        registrationMapper.updatePaymentStatus(id, true);
//        return ResponseEntity.ok().build();
//    }
}
