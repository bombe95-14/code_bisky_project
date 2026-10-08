package com.school.course.model;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

@Description("Session ou créneau de cours planifié dans l'emploi du temps")
public class CourseSession {

    @Id
    private String id;
    private Subject subject;
    private Teacher teacher;
    private String classroomId;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String room;

    public CourseSession() {
    }

    public CourseSession(String id, Subject subject, Teacher teacher, String classroomId, String dayOfWeek, String startTime, String endTime, String room) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.classroomId = classroomId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
