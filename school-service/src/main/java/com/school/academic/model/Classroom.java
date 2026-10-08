package com.school.academic.model;

import io.smallrye.graphql.api.federation.FieldSet;
import io.smallrye.graphql.api.federation.Key;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

@Key(fields = @FieldSet("id"))
@Description("Entite federée de base représentant une classe ou promotion au sein d'une école")
public class Classroom {

    @Id
    private String id;
    private String name;
    private String code;
    private String gradeLevel;
    private Integer capacity;
    private String schoolId;

    public Classroom() {
    }

    public Classroom(String id, String name, String code, String gradeLevel, Integer capacity, String schoolId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.gradeLevel = gradeLevel;
        this.capacity = capacity;
        this.schoolId = schoolId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
