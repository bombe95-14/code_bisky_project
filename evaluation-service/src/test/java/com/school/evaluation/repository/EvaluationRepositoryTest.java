package com.school.evaluation.repository;

import com.school.evaluation.model.AttendanceRecord;
import com.school.evaluation.model.AttendanceStatus;
import com.school.evaluation.model.Exam;
import com.school.evaluation.model.Grade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvaluationRepositoryTest {

    private EvaluationRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new EvaluationRepository();
    }

    @Test
    @DisplayName("Vérifie le calcul correct de la moyenne pondérée (GPA) pour stud-001")
    public void testCalculateGpaForStudent1() {
        Double gpa = repository.calculateGpa("stud-001");
        assertNotNull(gpa);
        // (18.5*2 + 16*1.5 + 14.5*2) / (2 + 1.5 + 2) = 90.0 / 5.5 = 16.36
        assertEquals(16.36, gpa, 0.01);
    }

    @Test
    @DisplayName("Vérifie le calcul correct du GPA pour stud-002")
    public void testCalculateGpaForStudent2() {
        Double gpa = repository.calculateGpa("stud-002");
        assertNotNull(gpa);
        // (19.0*2 + 17.5*1.5) / (2 + 1.5) = 64.25 / 3.5 = 18.36
        assertEquals(18.36, gpa, 0.01);
    }

    @Test
    @DisplayName("Vérifie que calculateGpa retourne null si l'étudiant n'a aucune note")
    public void testCalculateGpaWithoutGrades() {
        Double gpa = repository.calculateGpa("stud-unknown");
        assertNull(gpa);
    }

    @Test
    @DisplayName("Vérifie la récupération des pointages d'assiduité par étudiant")
    public void testFindAttendanceByStudentId() {
        List<AttendanceRecord> attendances = repository.findAttendanceByStudentId("stud-001");
        assertEquals(2, attendances.size());

        boolean hasRetard = attendances.stream().anyMatch(a -> a.getStatus() == AttendanceStatus.RETARD);
        boolean hasPresent = attendances.stream().anyMatch(a -> a.getStatus() == AttendanceStatus.PRESENT);
        assertTrue(hasRetard);
        assertTrue(hasPresent);
    }

    @Test
    @DisplayName("Vérifie l'enregistrement d'une nouvelle note")
    public void testSaveGrade() {
        Exam exam = repository.findExamById("exam-1");
        Grade newGrade = new Grade("gr-new", "stud-003", exam, 15.0, 20.0, "Très bien");
        repository.saveGrade(newGrade);

        List<Grade> studentGrades = repository.findGradesByStudentId("stud-003");
        assertEquals(1, studentGrades.size());
        assertEquals("gr-new", studentGrades.get(0).getId());
    }
}
