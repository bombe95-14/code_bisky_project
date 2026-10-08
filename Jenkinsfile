pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 1, unit: 'HOURS')
    }

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: true, description: 'Ignorer l\'exécution des tests unitaires et d\'intégration')
        booleanParam(name: 'BUILD_DOCKER', defaultValue: true, description: 'Construire les images Docker après compilation')
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository -Xmx1024m'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile & Test Subgraphs') {
            steps {
                script {
                    def testArgs = params.SKIP_TESTS ? '-DskipTests' : 'test'
                    echo "Lancement de la compilation et des tests pour les 4 microservices Quarkus..."
                    if (isUnix()) {
                        sh "mvn clean package ${testArgs}"
                    } else {
                        bat "mvn clean package ${testArgs}"
                    }
                }
            }
            post {
                always {
                    // Publication des rapports JUnit de tous les sous-modules
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Validate Apollo Supergraph') {
            steps {
                dir('apollo-router') {
                    script {
                        echo "Vérification des schémas et de la configuration Apollo Router..."
                        if (isUnix()) {
                            sh 'test -f supergraph.graphql && test -f router.yaml'
                            // Si rover CLI est installé sur l'agent Jenkins :
                            // sh 'rover supergraph compose --config supergraph.yaml'
                        } else {
                            bat 'powershell -Command "if (!(Test-Path supergraph.graphql) -or !(Test-Path router.yaml)) { exit 1 }"'
                        }
                    }
                }
            }
        }

        stage('Build Docker Containers') {
            when {
                expression { return params.BUILD_DOCKER }
            }
            steps {
                script {
                    echo "Construction des images de conteneurs pour les microservices..."
                    if (isUnix()) {
                        sh 'docker compose build'
                    } else {
                        bat 'docker compose build'
                    }
                }
            }
        }
    }

    post {
        success {
            echo '🎉 Pipeline School Management terminé avec succès !'
            archiveArtifacts artifacts: '**/target/quarkus-app/**', allowEmptyArchive: true
        }
        failure {
            echo '❌ Échec du build ou des tests sur le pipeline Jenkins.'
        }
    }
}
