package com.school.course.model;

import io.smallrye.graphql.api.federation.Extends;
import io.smallrye.graphql.api.federation.External;
import io.smallrye.graphql.api.federation.FieldSet;
import io.smallrye.graphql.api.federation.Key;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

import java.util.List;

@Extends
@Key(fields = @FieldSet("id"))
@Description("Extension du type fédéré Classroom pour y rattacher les cours et l'emploi du temps")
public class Classroom {

    @Id
    @External
    private String id;
    private List<CourseSession> courses;

    public Classroom() {
    }

    public Classroom(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CourseSession> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseSession> courses) {
        this.courses = courses;
    }
}
