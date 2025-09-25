package com.wms.dto;

public class CoachApplicationDTO {
    private String coachName;  // 教练姓名
    private String status;     // 格式化后的状态（未同意/已同意）

    // Getters and Setters
    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}