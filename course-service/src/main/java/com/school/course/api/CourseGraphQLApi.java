package com.school.course.api;

import com.school.course.model.Classroom;
import com.school.course.model.CourseSession;
import com.school.course.model.Subject;
import com.school.course.model.Teacher;
import com.school.course.repository.CourseRepository;
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
public class CourseGraphQLApi {

    @Inject
    CourseRepository courseRepository;

    @Query
    @Description("Résolveur de l'entité Teacher pour Apollo Federation")
    public Teacher teacher(@Id String id) {
        return courseRepository.findTeacherById(id);
    }

    @Query
    @Description("Liste de tous les enseignants")
    public List<Teacher> allTeachers() {
        return courseRepository.findAllTeachers();
    }

    @Query
    @Description("Liste des matières enseignées")
    public List<Subject> allSubjects() {
        return courseRepository.findAllSubjects();
    }

    @Query
    @Description("Liste de tous les cours programmés")
    public List<CourseSession> allCourses() {
        return courseRepository.findAllSessions();
    }

    @Query
    @Description("Résolveur fédéré de l'entité Classroom dans course-service")
    public Classroom classroom(@Id String id) {
        Classroom classroom = new Classroom(id);
        classroom.setCourses(courseRepository.findSessionsByClassroomId(id));
        return classroom;
    }

    @Mutation
    @Description("Programmer une nouvelle session de cours pour une classe")
    public CourseSession scheduleCourse(
            @Name("subjectId") String subjectId,
            @Name("teacherId") String teacherId,
            @Name("classroomId") String classroomId,
            @Name("dayOfWeek") String dayOfWeek,
            @Name("startTime") String startTime,
            @Name("endTime") String endTime,
            @Name("room") String room) {

        Subject subject = courseRepository.findSubjectById(subjectId);
        Teacher teacher = courseRepository.findTeacherById(teacherId);

        String id = "cs-" + UUID.randomUUID().toString().substring(0, 8);
        CourseSession session = new CourseSession(id, subject, teacher, classroomId, dayOfWeek, startTime, endTime, room);
        return courseRepository.saveSession(session);
    }
}
