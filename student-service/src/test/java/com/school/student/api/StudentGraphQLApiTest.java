package com.school.student.api;

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
public class StudentGraphQLApiTest {

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
    @DisplayName("Vérifie la requête allStudents")
    public void testAllStudentsQuery() {
        String query = """
            {
                "query": "{ allStudents { id firstName lastName matricule } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allStudents.size()", greaterThan(0))
            .body("data.allStudents[0].id", notNullValue());
    }

    @Test
    @DisplayName("Vérifie la requête student par ID")
    public void testStudentByIdQuery() {
        String query = """
            {
                "query": "{ student(id: \\"stud-001\\") { id firstName lastName email matricule classroomId } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.student.id", equalTo("stud-001"))
            .body("data.student.firstName", equalTo("Lucas"))
            .body("data.student.lastName", equalTo("Dubois"))
            .body("data.student.classroomId", equalTo("class-101"));
    }

    @Test
    @DisplayName("Vérifie le résolveur fédéré classroom qui fournit la liste des étudiants")
    public void testClassroomFederatedResolutionQuery() {
        String query = """
            {
                "query": "{ classroom(id: \\"class-101\\") { id students { id firstName lastName } } }"
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
            .body("data.classroom.students.size()", greaterThan(0))
            .body("data.classroom.students[0].firstName", notNullValue());
    }

    @Test
    @DisplayName("Vérifie la mutation enrollStudent")
    public void testEnrollStudentMutation() {
        String mutation = """
            {
                "query": "mutation { enrollStudent(firstName: \\"Nadia\\", lastName: \\"Benmoussa\\", email: \\"nadia.b@ecole.fr\\", matricule: \\"ETU-2026-777\\", classroomId: \\"class-101\\") { id firstName lastName email matricule } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(mutation)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.enrollStudent.id", notNullValue())
            .body("data.enrollStudent.firstName", equalTo("Nadia"))
            .body("data.enrollStudent.matricule", equalTo("ETU-2026-777"));
    }
}
