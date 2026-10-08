package com.school.evaluation.model;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

@Description("Note obtenue par un élève à un examen")
public class Grade {

    @Id
    private String id;
    private String studentId;
    private Exam exam;
    private Double score;
    private Double maxScore;
    private String comment;

    public Grade() {
    }

    public Grade(String id, String studentId, Exam exam, Double score, Double maxScore, String comment) {
        this.id = id;
        this.studentId = studentId;
        this.exam = exam;
        this.score = score;
        this.maxScore = maxScore;
        this.comment = comment;
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

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
