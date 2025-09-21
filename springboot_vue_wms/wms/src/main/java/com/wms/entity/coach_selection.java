package com.wms.entity;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;

@Entity
@Table(name="coach_selection")
@TableName("coach_selection")
public class coach_selection {
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

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public Integer getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(Integer selectionId) {
        this.selectionId = selectionId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "coach_selection{" +
                "selectionId=" + selectionId +
                ", studentId=" + studentId +
                ", coachId=" + coachId +
                ", status='" + status + '\'' +
                '}';
    }
}
