package com.school.course.model;

import org.eclipse.microprofile.graphql.Id;

public class Subject {
    @Id
    private String id;
    private String name;
    private String code;
    private Integer coefficient;

    public Subject() {
    }

    public Subject(String id, String name, String code, Integer coefficient) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.coefficient = coefficient;
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

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }
}
