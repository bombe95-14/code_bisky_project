package com.school.student.model;

import io.smallrye.graphql.api.federation.Extends;
import io.smallrye.graphql.api.federation.External;
import io.smallrye.graphql.api.federation.FieldSet;
import io.smallrye.graphql.api.federation.Key;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Id;

import java.util.List;

@Extends
@Key(fields = @FieldSet("id"))
@Description("Extension du type fédéré Classroom pour y attacher la liste des élèves")
public class Classroom {

    @Id
    @External
    private String id;
    private List<Student> students;

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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
