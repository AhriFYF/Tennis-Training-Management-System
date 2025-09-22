package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_schedules")
public class CourseSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @Column(nullable = false)
    private Boolean available = true;

    @OneToOne(mappedBy = "schedule")
    private CourseBooking booking;

    // Getters and Setters
}
