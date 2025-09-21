package com.wms.entity;
import javax.persistence.*;

@Table(name="coach_selection")
@Entity
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
    private String status;//没同意0   同意1

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
