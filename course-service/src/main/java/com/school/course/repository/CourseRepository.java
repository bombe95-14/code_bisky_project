package com.school.course.repository;

import com.school.course.model.CourseSession;
import com.school.course.model.Subject;
import com.school.course.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CourseRepository {

    private final Map<String, Teacher> teachers = new ConcurrentHashMap<>();
    private final Map<String, Subject> subjects = new ConcurrentHashMap<>();
    private final Map<String, CourseSession> sessions = new ConcurrentHashMap<>();

    public CourseRepository() {
        // Seed teachers
        Teacher t1 = new Teacher("prof-1", "Marc", "Durand", "marc.durand@ecole.fr", "Mathématiques");
        Teacher t2 = new Teacher("prof-2", "Hélène", "Petit", "helene.petit@ecole.fr", "Physique-Chimie");
        Teacher t3 = new Teacher("prof-3", "David", "Rousseau", "david.rousseau@ecole.fr", "Philosophie");
        teachers.put(t1.getId(), t1);
        teachers.put(t2.getId(), t2);
        teachers.put(t3.getId(), t3);

        // Seed subjects
        Subject sub1 = new Subject("sub-1", "Mathématiques Avancées", "MATH-ADV", 6);
        Subject sub2 = new Subject("sub-2", "Physique Quantique & Ondes", "PHYS-01", 5);
        Subject sub3 = new Subject("sub-3", "Philosophie Contemporaine", "PHIL-01", 3);
        subjects.put(sub1.getId(), sub1);
        subjects.put(sub2.getId(), sub2);
        subjects.put(sub3.getId(), sub3);

        // Seed sessions for class-101
        CourseSession cs1 = new CourseSession("cs-1", sub1, t1, "class-101", "LUNDI", "08:30", "10:30", "Salle 204");
        CourseSession cs2 = new CourseSession("cs-2", sub2, t2, "class-101", "MARDI", "10:45", "12:45", "Laboratoire B");
        CourseSession cs3 = new CourseSession("cs-3", sub3, t3, "class-101", "JEUDI", "14:00", "16:00", "Amphi A");

        // Seed sessions for class-102
        CourseSession cs4 = new CourseSession("cs-4", sub2, t2, "class-102", "LUNDI", "10:45", "12:45", "Laboratoire B");

        sessions.put(cs1.getId(), cs1);
        sessions.put(cs2.getId(), cs2);
        sessions.put(cs3.getId(), cs3);
        sessions.put(cs4.getId(), cs4);
    }

    public Teacher findTeacherById(String id) {
        return teachers.get(id);
    }

    public List<Teacher> findAllTeachers() {
        return new ArrayList<>(teachers.values());
    }

    public Subject findSubjectById(String id) {
        return subjects.get(id);
    }

    public List<Subject> findAllSubjects() {
        return new ArrayList<>(subjects.values());
    }

    public List<CourseSession> findAllSessions() {
        return new ArrayList<>(sessions.values());
    }

    public List<CourseSession> findSessionsByClassroomId(String classroomId) {
        return sessions.values().stream()
                .filter(s -> classroomId != null && classroomId.equals(s.getClassroomId()))
                .toList();
    }

    public CourseSession saveSession(CourseSession session) {
        sessions.put(session.getId(), session);
        return session;
    }
}
