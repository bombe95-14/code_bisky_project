package com.school.evaluation.model;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

@Description("Examen ou devoir surveillé")
public class Exam {

    @Id
    private String id;
    private String title;
    private String subjectCode;
    private String examDate;
    private Double coefficient;

    public Exam() {
    }

    public Exam(String id, String title, String subjectCode, String examDate, Double coefficient) {
        this.id = id;
        this.title = title;
        this.subjectCode = subjectCode;
        this.examDate = examDate;
        this.coefficient = coefficient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
