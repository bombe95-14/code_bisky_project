package com.school.academic.model;

import org.eclipse.microprofile.graphql.Id;

public class School {
    @Id
    private String id;
    private String name;
    private String code;
    private String address;
    private String city;

    public School() {
    }

    public School(String id, String name, String code, String address, String city) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.address = address;
        this.city = city;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
