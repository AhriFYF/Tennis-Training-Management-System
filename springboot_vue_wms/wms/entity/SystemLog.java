package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_logs")
public class SystemLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String actionType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String actionDetail;

    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime;

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String requestData;

    // Getters and Setters
}
