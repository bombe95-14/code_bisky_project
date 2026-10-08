package com.school.evaluation.repository;

import com.school.evaluation.model.AttendanceRecord;
import com.school.evaluation.model.AttendanceStatus;
import com.school.evaluation.model.Exam;
import com.school.evaluation.model.Grade;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class EvaluationRepository {

    private final Map<String, Exam> exams = new ConcurrentHashMap<>();
    private final Map<String, Grade> grades = new ConcurrentHashMap<>();
    private final Map<String, AttendanceRecord> attendances = new ConcurrentHashMap<>();

    public EvaluationRepository() {
        // Seed exams
        Exam ex1 = new Exam("exam-1", "Contrôle Continu 1 - Algèbre Linéaire", "MATH-ADV", "2026-10-15", 2.0);
        Exam ex2 = new Exam("exam-2", "TP Noté - Mécanique Quantique", "PHYS-01", "2026-10-22", 1.5);
        Exam ex3 = new Exam("exam-3", "Dissertation - Morale et Société", "PHIL-01", "2026-11-05", 2.0);
        exams.put(ex1.getId(), ex1);
        exams.put(ex2.getId(), ex2);
        exams.put(ex3.getId(), ex3);

        // Seed grades for stud-001 (Lucas Dubois)
        Grade g1 = new Grade("gr-1", "stud-001", ex1, 18.5, 20.0, "Excellent travail de réflexion");
        Grade g2 = new Grade("gr-2", "stud-001", ex2, 16.0, 20.0, "Très bonne maîtrise pratique");
        Grade g3 = new Grade("gr-3", "stud-001", ex3, 14.5, 20.0, "Bonne argumentation");

        // Seed grades for stud-002 (Emma Leroy)
        Grade g4 = new Grade("gr-4", "stud-002", ex1, 19.0, 20.0, "Parfait, sans aucune faute");
        Grade g5 = new Grade("gr-5", "stud-002", ex2, 17.5, 20.0, "Rigueur exemplaire");

        // Seed grades for stud-004 (Chloé Laurent)
        Grade g6 = new Grade("gr-6", "stud-004", ex2, 15.0, 20.0, "Bien");

        grades.put(g1.getId(), g1);
        grades.put(g2.getId(), g2);
        grades.put(g3.getId(), g3);
        grades.put(g4.getId(), g4);
        grades.put(g5.getId(), g5);
        grades.put(g6.getId(), g6);

        // Seed attendances
        AttendanceRecord att1 = new AttendanceRecord("att-1", "stud-001", "2026-09-15", "Mathématiques", AttendanceStatus.PRESENT);
        AttendanceRecord att2 = new AttendanceRecord("att-2", "stud-001", "2026-09-16", "Physique", AttendanceStatus.RETARD);
        AttendanceRecord att3 = new AttendanceRecord("att-3", "stud-002", "2026-09-15", "Mathématiques", AttendanceStatus.PRESENT);
        AttendanceRecord att4 = new AttendanceRecord("att-4", "stud-003", "2026-09-15", "Mathématiques", AttendanceStatus.ABSENT_JUSTIFIE);

        attendances.put(att1.getId(), att1);
        attendances.put(att2.getId(), att2);
        attendances.put(att3.getId(), att3);
        attendances.put(att4.getId(), att4);
    }

    public Exam findExamById(String id) {
        return exams.get(id);
    }

    public List<Exam> findAllExams() {
        return new ArrayList<>(exams.values());
    }

    public List<Grade> findAllGrades() {
        return new ArrayList<>(grades.values());
    }

    public List<Grade> findGradesByStudentId(String studentId) {
        return grades.values().stream()
                .filter(g -> studentId != null && studentId.equals(g.getStudentId()))
                .toList();
    }

    public Double calculateGpa(String studentId) {
        List<Grade> studentGrades = findGradesByStudentId(studentId);
        if (studentGrades.isEmpty()) {
            return null;
        }
        double totalWeightedScore = 0.0;
        double totalCoeff = 0.0;
        for (Grade g : studentGrades) {
            double coeff = (g.getExam() != null && g.getExam().getCoefficient() != null)
                    ? g.getExam().getCoefficient() : 1.0;
            // standardise sur base 20
            double normalized = (g.getScore() / g.getMaxScore()) * 20.0;
            totalWeightedScore += normalized * coeff;
            totalCoeff += coeff;
        }
        return Math.round((totalWeightedScore / totalCoeff) * 100.0) / 100.0;
    }

    public List<AttendanceRecord> findAttendanceByStudentId(String studentId) {
        return attendances.values().stream()
                .filter(a -> studentId != null && studentId.equals(a.getStudentId()))
                .toList();
    }

    public Grade saveGrade(Grade grade) {
        grades.put(grade.getId(), grade);
        return grade;
    }

    public AttendanceRecord saveAttendance(AttendanceRecord attendance) {
        attendances.put(attendance.getId(), attendance);
        return attendance;
    }
}
