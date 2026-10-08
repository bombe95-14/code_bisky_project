package com.school.evaluation.model;

import io.smallrye.graphql.api.federation.Extends;
import io.smallrye.graphql.api.federation.External;
import io.smallrye.graphql.api.federation.FieldSet;
import io.smallrye.graphql.api.federation.Key;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

import java.util.List;

@Extends
@Key(fields = @FieldSet("id"))
@Description("Extension du type fédéré Student pour y rattacher les notes, la moyenne et l'assiduité")
public class Student {

    @Id
    @External
    private String id;
    private List<Grade> grades;
    private Double gpa;
    private List<AttendanceRecord> attendances;

    public Student() {
    }

    public Student(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public List<AttendanceRecord> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceRecord> attendances) {
        this.attendances = attendances;
    }
}
