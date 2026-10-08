package com.school.course.model;

import io.smallrye.graphql.api.federation.FieldSet;
import io.smallrye.graphql.api.federation.Key;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

@Key(fields = @FieldSet("id"))
@Description("Entité représentant un enseignant")
public class Teacher {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialty;

    public Teacher() {
    }

    public Teacher(String id, String firstName, String lastName, String email, String specialty) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialty = specialty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
