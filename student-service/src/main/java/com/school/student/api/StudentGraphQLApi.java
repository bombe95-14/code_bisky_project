package com.school.student.api;

import com.school.student.model.Classroom;
import com.school.student.model.Student;
import com.school.student.repository.StudentRepository;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Id;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.UUID;

@GraphQLApi
public class StudentGraphQLApi {

    @Inject
    StudentRepository studentRepository;

    @Query
    @Description("Résolveur de l'entité Student pour Apollo Federation et requêtes directes")
    public Student student(@Id String id) {
        return studentRepository.findById(id);
    }

    @Query
    @Description("Liste de tous les élèves enregistrés")
    public List<Student> allStudents() {
        return studentRepository.findAll();
    }

    @Query
    @Description("Résolveur fédéré de l'entité Classroom dans student-service")
    public Classroom classroom(@Id String id) {
        Classroom classroom = new Classroom(id);
        classroom.setStudents(studentRepository.findByClassroomId(id));
        return classroom;
    }

    @Mutation
    @Description("Inscrire un nouvel élève et l'assigner à une classe")
    public Student enrollStudent(
            @Name("firstName") String firstName,
            @Name("lastName") String lastName,
            @Name("email") String email,
            @Name("matricule") String matricule,
            @Name("classroomId") String classroomId) {

        String id = "stud-" + UUID.randomUUID().toString().substring(0, 8);
        Student student = new Student(id, firstName, lastName, email, matricule, classroomId);
        return studentRepository.save(student);
    }
}
