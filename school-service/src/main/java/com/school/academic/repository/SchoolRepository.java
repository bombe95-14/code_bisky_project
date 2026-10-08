package com.school.academic.repository;

import com.school.academic.model.Classroom;
import com.school.academic.model.School;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SchoolRepository {

    private final Map<String, School> schools = new ConcurrentHashMap<>();
    private final Map<String, Classroom> classrooms = new ConcurrentHashMap<>();

    public SchoolRepository() {
        // Seed initial schools
        School school1 = new School("school-1", "Lycée d'Excellence Victor Hugo", "LEVH-01", "12 Boulevard Saint-Germain", "Paris");
        School school2 = new School("school-2", "Collège & Lycée International Descartes", "CLID-02", "45 Rue des Écoles", "Lyon");
        schools.put(school1.getId(), school1);
        schools.put(school2.getId(), school2);

        // Seed initial classrooms
        Classroom class1 = new Classroom("class-101", "Terminale S1 - Mathématiques", "TS1-MATH", "Terminale", 32, "school-1");
        Classroom class2 = new Classroom("class-102", "Terminale S2 - Physique & Chimie", "TS2-PC", "Terminale", 30, "school-1");
        Classroom class3 = new Classroom("class-201", "Première Générale A", "1GEN-A", "Première", 35, "school-2");
        classrooms.put(class1.getId(), class1);
        classrooms.put(class2.getId(), class2);
        classrooms.put(class3.getId(), class3);
    }

    public School findSchoolById(String id) {
        return schools.get(id);
    }

    public List<School> findAllSchools() {
        return new ArrayList<>(schools.values());
    }

    public Classroom findClassroomById(String id) {
        return classrooms.get(id);
    }

    public List<Classroom> findAllClassrooms() {
        return new ArrayList<>(classrooms.values());
    }

    public List<Classroom> findClassroomsBySchoolId(String schoolId) {
        return classrooms.values().stream()
                .filter(c -> schoolId.equals(c.getSchoolId()))
                .toList();
    }

    public Classroom saveClassroom(Classroom classroom) {
        classrooms.put(classroom.getId(), classroom);
        return classroom;
    }
}
