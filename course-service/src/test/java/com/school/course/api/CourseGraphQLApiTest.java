package com.school.course.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
public class CourseGraphQLApiTest {

    @Test
    @DisplayName("Vérifie que le point de contrôle de santé (/q/health) répond UP")
    public void testHealthCheck() {
        given()
        .when()
            .get("/q/health")
        .then()
            .statusCode(200)
            .body("status", is("UP"));
    }

    @Test
    @DisplayName("Vérifie les requêtes allTeachers et teacher par ID")
    public void testTeachersQueries() {
        String allTeachersQuery = """
            {
                "query": "{ allTeachers { id firstName lastName specialty } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(allTeachersQuery)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allTeachers.size()", greaterThan(0))
            .body("data.allTeachers[0].id", notNullValue());

        String teacherByIdQuery = """
            {
                "query": "{ teacher(id: \\"prof-1\\") { id firstName lastName specialty } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(teacherByIdQuery)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.teacher.id", equalTo("prof-1"))
            .body("data.teacher.lastName", equalTo("Durand"))
            .body("data.teacher.specialty", equalTo("Mathématiques"));
    }

    @Test
    @DisplayName("Vérifie la requête allSubjects")
    public void testAllSubjectsQuery() {
        String query = """
            {
                "query": "{ allSubjects { id name code coefficient } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allSubjects.size()", greaterThan(0))
            .body("data.allSubjects[0].name", notNullValue());
    }

    @Test
    @DisplayName("Vérifie la requête allCourses avec objets imbriqués subject et teacher")
    public void testAllCoursesQuery() {
        String query = """
            {
                "query": "{ allCourses { id dayOfWeek startTime endTime room subject { name code } teacher { lastName } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allCourses.size()", greaterThan(0))
            .body("data.allCourses[0].subject.name", notNullValue())
            .body("data.allCourses[0].teacher.lastName", notNullValue());
    }

    @Test
    @DisplayName("Vérifie la résolution fédérée des cours d'une classe")
    public void testClassroomFederatedResolutionQuery() {
        String query = """
            {
                "query": "{ classroom(id: \\"class-101\\") { id courses { id dayOfWeek room subject { name } } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.classroom.id", equalTo("class-101"))
            .body("data.classroom.courses.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Vérifie la mutation scheduleCourse")
    public void testScheduleCourseMutation() {
        String mutation = """
            {
                "query": "mutation { scheduleCourse(subjectId: \\"sub-1\\", teacherId: \\"prof-1\\", classroomId: \\"class-101\\", dayOfWeek: \\"VENDREDI\\", startTime: \\"14:00\\", endTime: \\"16:00\\", room: \\"Salle 105\\") { id dayOfWeek startTime endTime room subject { id } teacher { id } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(mutation)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.scheduleCourse.id", notNullValue())
            .body("data.scheduleCourse.dayOfWeek", equalTo("VENDREDI"))
            .body("data.scheduleCourse.room", equalTo("Salle 105"))
            .body("data.scheduleCourse.subject.id", equalTo("sub-1"))
            .body("data.scheduleCourse.teacher.id", equalTo("prof-1"));
    }
}
