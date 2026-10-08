package com.school.evaluation.api;

import com.school.evaluation.model.AttendanceRecord;
import com.school.evaluation.model.AttendanceStatus;
import com.school.evaluation.model.Exam;
import com.school.evaluation.model.Grade;
import com.school.evaluation.model.Student;
import com.school.evaluation.repository.EvaluationRepository;
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
public class EvaluationGraphQLApi {

    @Inject
    EvaluationRepository evaluationRepository;

    @Query
    @Description("Liste de tous les examens programmés")
    public List<Exam> allExams() {
        return evaluationRepository.findAllExams();
    }

    @Query
    @Description("Obtenir un examen par son identifiant")
    public Exam exam(@Id String id) {
        return evaluationRepository.findExamById(id);
    }

    @Query
    @Description("Liste globale de toutes les notes attribuées")
    public List<Grade> allGrades() {
        return evaluationRepository.findAllGrades();
    }

    @Query
    @Description("Résolveur fédéré de l'entité Student dans evaluation-service")
    public Student student(@Id String id) {
        Student student = new Student(id);
        student.setGrades(evaluationRepository.findGradesByStudentId(id));
        student.setGpa(evaluationRepository.calculateGpa(id));
        student.setAttendances(evaluationRepository.findAttendanceByStudentId(id));
        return student;
    }

    @Mutation
    @Description("Enregistrer une nouvelle note pour un élève")
    public Grade recordGrade(
            @Name("studentId") String studentId,
            @Name("examId") String examId,
            @Name("score") Double score,
            @Name("maxScore") Double maxScore,
            @Name("comment") String comment) {

        Exam exam = evaluationRepository.findExamById(examId);
        String id = "gr-" + UUID.randomUUID().toString().substring(0, 8);
        Grade grade = new Grade(id, studentId, exam, score, maxScore, comment);
        return evaluationRepository.saveGrade(grade);
    }

    @Mutation
    @Description("Enregistrer un pointage de présence ou absence pour un élève")
    public AttendanceRecord recordAttendance(
            @Name("studentId") String studentId,
            @Name("date") String date,
            @Name("sessionName") String sessionName,
            @Name("status") AttendanceStatus status) {

        String id = "att-" + UUID.randomUUID().toString().substring(0, 8);
        AttendanceRecord attendance = new AttendanceRecord(id, studentId, date, sessionName, status);
        return evaluationRepository.saveAttendance(attendance);
    }
}
