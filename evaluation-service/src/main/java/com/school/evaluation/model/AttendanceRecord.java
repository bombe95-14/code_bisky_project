package com.school.evaluation.model;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

@Description("Enregistrement de présence ou d'absence d'un élève")
public class AttendanceRecord {

    @Id
    private String id;
    private String studentId;
    private String date;
    private String sessionName;
    private AttendanceStatus status;

    public AttendanceRecord() {
    }

    public AttendanceRecord(String id, String studentId, String date, String sessionName, AttendanceStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.sessionName = sessionName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }
}
