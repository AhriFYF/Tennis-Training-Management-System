package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_records")
public class PaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false)
    private String transactionId;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Column(nullable = false)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String remark;

    // Getters and Setters
}

