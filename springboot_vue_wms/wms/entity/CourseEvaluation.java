package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_evaluations")
public class CourseEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private CourseBooking booking;

    @Column(columnDefinition = "TEXT")
    private String studentFeedback;

    @Column(columnDefinition = "TEXT")
    private String coachFeedback;

    @Column(name = "student_submit_time")
    private LocalDateTime studentSubmitTime;

    @Column(name = "coach_submit_time")
    private LocalDateTime coachSubmitTime;

    // Getters and Setters
}
