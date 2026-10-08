package com.school.academic.api;

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
public class SchoolGraphQLApiTest {

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
    @DisplayName("Vérifie la requête allSchools et la résolution imbriquée des classrooms")
    public void testAllSchoolsQuery() {
        String query = """
            {
                "query": "{ allSchools { id name classrooms { id name } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allSchools.size()", greaterThan(0))
            .body("data.allSchools[0].id", notNullValue())
            .body("data.allSchools[0].classrooms.size()", greaterThan(0));
    }

    @Test
    @DisplayName("Vérifie la requête school par ID")
    public void testSchoolByIdQuery() {
        String query = """
            {
                "query": "{ school(id: \\"school-1\\") { id name city classrooms { id } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.school.id", equalTo("school-1"))
            .body("data.school.name", equalTo("Lycée d'Excellence Victor Hugo"))
            .body("data.school.city", equalTo("Paris"));
    }

    @Test
    @DisplayName("Vérifie la requête classroom par ID")
    public void testClassroomByIdQuery() {
        String query = """
            {
                "query": "{ classroom(id: \\"class-101\\") { id name capacity gradeLevel } }"
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
            .body("data.classroom.name", equalTo("Terminale S1 - Mathématiques"))
            .body("data.classroom.capacity", equalTo(32));
    }

    @Test
    @DisplayName("Vérifie la mutation createClassroom")
    public void testCreateClassroomMutation() {
        String mutation = """
            {
                "query": "mutation { createClassroom(name: \\"Terminale Test\\", code: \\"TT-01\\", gradeLevel: \\"Terminale\\", capacity: 25, schoolId: \\"school-1\\") { id name code capacity } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(mutation)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.createClassroom.id", notNullValue())
            .body("data.createClassroom.name", equalTo("Terminale Test"))
            .body("data.createClassroom.code", equalTo("TT-01"));
    }
}
