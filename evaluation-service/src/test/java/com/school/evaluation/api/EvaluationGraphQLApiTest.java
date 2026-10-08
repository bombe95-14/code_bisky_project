package com.school.evaluation.api;

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
public class EvaluationGraphQLApiTest {

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
    @DisplayName("Vérifie la requête allExams et exam par ID")
    public void testExamsQueries() {
        String allExamsQuery = """
            {
                "query": "{ allExams { id title subjectCode coefficient } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(allExamsQuery)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allExams.size()", greaterThan(0))
            .body("data.allExams[0].title", notNullValue());

        String examByIdQuery = """
            {
                "query": "{ exam(id: \\"exam-1\\") { id title subjectCode coefficient } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(examByIdQuery)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.exam.id", equalTo("exam-1"))
            .body("data.exam.title", equalTo("Contrôle Continu 1 - Algèbre Linéaire"));
    }

    @Test
    @DisplayName("Vérifie la requête allGrades avec objet imbriqué exam")
    public void testAllGradesQuery() {
        String query = """
            {
                "query": "{ allGrades { id score maxScore exam { title subjectCode } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(query)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.allGrades.size()", greaterThan(0))
            .body("data.allGrades[0].exam.title", notNullValue());
    }

    @Test
    @DisplayName("Vérifie le résolveur fédéré student calculant le GPA et rattachant notes et assiduité")
    public void testStudentFederatedResolutionQuery() {
        String query = """
            {
                "query": "{ student(id: \\"stud-001\\") { id gpa grades { score maxScore } attendances { status } } }"
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
            .body("data.student.gpa", equalTo(16.36f))
            .body("data.student.grades.size()", equalTo(3))
            .body("data.student.attendances.size()", equalTo(2));
    }

    @Test
    @DisplayName("Vérifie la mutation recordGrade")
    public void testRecordGradeMutation() {
        String mutation = """
            {
                "query": "mutation { recordGrade(studentId: \\"stud-003\\", examId: \\"exam-1\\", score: 17.5, maxScore: 20.0, comment: \\"Très bonne prestation\\") { id score maxScore comment exam { id } } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(mutation)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.recordGrade.id", notNullValue())
            .body("data.recordGrade.score", equalTo(17.5f))
            .body("data.recordGrade.maxScore", equalTo(20.0f))
            .body("data.recordGrade.comment", equalTo("Très bonne prestation"))
            .body("data.recordGrade.exam.id", equalTo("exam-1"));
    }

    @Test
    @DisplayName("Vérifie la mutation recordAttendance")
    public void testRecordAttendanceMutation() {
        String mutation = """
            {
                "query": "mutation { recordAttendance(studentId: \\"stud-002\\", date: \\"2026-09-20\\", sessionName: \\"Mathématiques\\", status: PRESENT) { id studentId date sessionName status } }"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(mutation)
        .when()
            .post("/graphql")
        .then()
            .statusCode(200)
            .body("data.recordAttendance.id", notNullValue())
            .body("data.recordAttendance.studentId", equalTo("stud-002"))
            .body("data.recordAttendance.sessionName", equalTo("Mathématiques"))
            .body("data.recordAttendance.status", equalTo("PRESENT"));
    }
}
