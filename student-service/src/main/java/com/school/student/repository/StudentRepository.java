package com.school.student.repository;

import com.school.student.model.Student;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class StudentRepository {

    private final Map<String, Student> students = new ConcurrentHashMap<>();

    public StudentRepository() {
        // Seed students for class-101
        Student s1 = new Student("stud-001", "Lucas", "Dubois", "lucas.dubois@ecole.fr", "ETU-2026-001", "class-101");
        Student s2 = new Student("stud-002", "Emma", "Leroy", "emma.leroy@ecole.fr", "ETU-2026-002", "class-101");
        Student s3 = new Student("stud-003", "Thomas", "Moreau", "thomas.moreau@ecole.fr", "ETU-2026-003", "class-101");

        // Seed students for class-102
        Student s4 = new Student("stud-004", "Chloé", "Laurent", "chloe.laurent@ecole.fr", "ETU-2026-004", "class-102");
        Student s5 = new Student("stud-005", "Yassine", "Benali", "yassine.benali@ecole.fr", "ETU-2026-005", "class-102");

        students.put(s1.getId(), s1);
        students.put(s2.getId(), s2);
        students.put(s3.getId(), s3);
        students.put(s4.getId(), s4);
        students.put(s5.getId(), s5);
    }

    public Student findById(String id) {
        return students.get(id);
    }

    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }

    public List<Student> findByClassroomId(String classroomId) {
        return students.values().stream()
                .filter(s -> classroomId != null && classroomId.equals(s.getClassroomId()))
                .toList();
    }

    public Student save(Student student) {
        students.put(student.getId(), student);
        return student;
    }
}
