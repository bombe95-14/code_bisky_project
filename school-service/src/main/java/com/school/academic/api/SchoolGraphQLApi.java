package com.school.academic.api;

import com.school.academic.model.Classroom;
import com.school.academic.model.School;
import com.school.academic.repository.SchoolRepository;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Id;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

import java.util.List;
import java.util.UUID;

@GraphQLApi
public class SchoolGraphQLApi {

    @Inject
    SchoolRepository schoolRepository;

    @Query
    @Description("Résolveur de l'entité Classroom pour Apollo Federation et requêtes directes")
    public Classroom classroom(@Id String id) {
        return schoolRepository.findClassroomById(id);
    }

    @Query
    @Description("Liste de toutes les classes enregistrées")
    public List<Classroom> allClassrooms() {
        return schoolRepository.findAllClassrooms();
    }

    @Query
    @Description("Obtenir un établissement par son identifiant")
    public School school(@Id String id) {
        return schoolRepository.findSchoolById(id);
    }

    @Query
    @Description("Liste de tous les établissements scolaires")
    public List<School> allSchools() {
        return schoolRepository.findAllSchools();
    }

    @Description("Classes rattachées à un établissement")
    public List<Classroom> classrooms(@Source School school) {
        return schoolRepository.findClassroomsBySchoolId(school.getId());
    }

    @Mutation
    @Description("Créer une nouvelle classe au sein d'une école")
    public Classroom createClassroom(
            @Name("name") String name,
            @Name("code") String code,
            @Name("gradeLevel") String gradeLevel,
            @Name("capacity") Integer capacity,
            @Name("schoolId") String schoolId) {

        String newId = "class-" + UUID.randomUUID().toString().substring(0, 8);
        Classroom classroom = new Classroom(newId, name, code, gradeLevel, capacity, schoolId);
        return schoolRepository.saveClassroom(classroom);
    }
}
