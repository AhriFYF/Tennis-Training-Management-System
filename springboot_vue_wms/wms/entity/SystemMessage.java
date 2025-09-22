package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_messages")
public class SystemMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;

    @Column(name = "read_time")
    private LocalDateTime readTime;

    @Column(nullable = false)
    private String messageType; // e.g., "BOOKING_CONFIRM", "COACH_CHANGE", etc.

    @Column(columnDefinition = "TEXT")
    private String relatedData; // JSON data related to the message

    // Getters and Setters
}
