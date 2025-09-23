package com.wms.dto;

public class CoachSelectionDTO {
    private Integer selectionId;
    private Integer studentId;
    private Integer coachId;
    private String status;
    private String studentName;
    private String studentPhone;
    private Integer studentAge;
    private Integer studentSex; // 0-女, 1-男

    // 构造方法
    public CoachSelectionDTO() {}

    public CoachSelectionDTO(Integer selectionId, Integer studentId, Integer coachId,
                             String status, String studentName, String studentPhone,
                             Integer studentAge, Integer studentSex) {
        this.selectionId = selectionId;
        this.studentId = studentId;
        this.coachId = coachId;
        this.status = status;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.studentAge = studentAge;
        this.studentSex = studentSex;
    }

    // Getter和Setter方法
    public Integer getSelectionId() { return selectionId; }
    public void setSelectionId(Integer selectionId) { this.selectionId = selectionId; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getCoachId() { return coachId; }
    public void setCoachId(Integer coachId) { this.coachId = coachId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentPhone() { return studentPhone; }
    public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }

    public Integer getStudentAge() { return studentAge; }
    public void setStudentAge(Integer studentAge) { this.studentAge = studentAge; }

    public Integer getStudentSex() { return studentSex; }
    public void setStudentSex(Integer studentSex) { this.studentSex = studentSex; }

    @Override
    public String toString() {
        return "CoachSelectionDTO{" +
                "selectionId=" + selectionId +
                ", studentId=" + studentId +
                ", coachId=" + coachId +
                ", status='" + status + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentPhone='" + studentPhone + '\'' +
                ", studentAge=" + studentAge +
                ", studentSex=" + studentSex +
                '}';
    }
}