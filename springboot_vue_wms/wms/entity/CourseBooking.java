package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_bookings")
public class CourseBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private CourseSchedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    @Column(name = "confirm_time")
    private LocalDateTime confirmTime;

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;

    @Column(name = "cancel_initiator")
    private String cancelInitiator; // "student" or "coach"

    @Column(name = "cancel_confirmed")
    private Boolean cancelConfirmed;

    @Column(name = "fee_amount", nullable = false)
    private BigDecimal feeAmount;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = false;

    @OneToOne(mappedBy = "booking")
    private CourseEvaluation evaluation;

    // Getters and Setters
}

