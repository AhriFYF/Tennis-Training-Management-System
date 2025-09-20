package com.wms.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import javax.persistence.*;

@Table(name="course_selection")
@Entity
public class course_selection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selection_id")
    private Integer selectionId;

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "coach_id")
    private Integer coachId;

    @Column(name = "status")
    private String status;
}
