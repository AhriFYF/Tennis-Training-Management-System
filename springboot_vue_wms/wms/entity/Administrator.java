package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "administrators")
@PrimaryKeyJoinColumn(name = "user_id")
public class Administrator extends User {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminRole role;

    // Getters and Setters
}

