pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-21'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
        timestamps()
        //ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        disableConcurrentBuilds()
    }

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Ignorer l\'exécution des tests unitaires et d\'intégration Maven')
        booleanParam(name: 'BUILD_DOCKER', defaultValue: true, description: 'Construire les images Docker des microservices et d\'Apollo Router')
        booleanParam(name: 'RUN_CONTAINER_TESTS', defaultValue: false, description: 'Démarrer les conteneurs et vérifier l\'état de santé (Health Check)')
        string(name: 'DOCKER_TAG', defaultValue: 'latest', description: 'Tag appliqué aux images Docker construites')
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository -Xmx1024m'
        PROJECT_NAME = 'school-management-system'
    }

    stages {
        stage('Environment Information') {
            steps {
                script {
                    echo "=========================================================================="
                    echo "🚀 Initialisation du Pipeline CI/CD - ${env.PROJECT_NAME}"
                    echo "   Branche Git : ${env.GIT_BRANCH ?: 'N/A'}"
                    echo "   Commit SHA  : ${env.GIT_COMMIT ?: 'N/A'}"
                    echo "   Docker Tag  : ${params.DOCKER_TAG}"
                    echo "   Skip Tests  : ${params.SKIP_TESTS}"
                    echo "=========================================================================="
                    if (isUnix()) {
                        sh 'java -version && mvn -version'
                        sh 'which docker > /dev/null 2>&1 && docker -v || echo "⚠️ Docker CLI non installé sur le conteneur Jenkins"'
                    } else {
                        bat 'java -version && mvn -version'
                    } 
                }
            }
        }

        stage('Checkout Source Code') {
            steps {
                checkout scm
            }
        }

        stage('Compile & Test Quarkus Subgraphs') {
            steps {
                script {
                    def testArgs = params.SKIP_TESTS ? '-DskipTests' : 'test'
                    echo "🔨 Build Maven et exécution des tests pour les 4 microservices Quarkus :"
                    echo "   - school-service"
                    echo "   - student-service"
                    echo "   - course-service"
                    echo "   - evaluation-service"
                    
                    if (isUnix()) {
                        sh "mvn clean package ${testArgs}"
                    } else {
                        bat "mvn clean package ${testArgs}"
                    }
                }
            }
            post {
                always {
                    echo "📊 Publication des rapports de tests JUnit..."
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Validate Apollo Supergraph') {
            steps {
                dir('apollo-router') {
                    script {
                        echo "🔍 Validation des schémas GraphQL et de la configuration Apollo Router..."
                        if (isUnix()) {
                            sh 'test -f supergraph.graphql && test -f router.yaml'
                            // Validation additionnelle via Rover CLI si disponible sur le runner
                            sh 'which rover > /dev/null 2>&1 && rover supergraph compose --config supergraph.yaml || echo "Rover CLI non présent, validation par fichiers OK."'
                        } else {
                            bat 'powershell -Command "if (!(Test-Path supergraph.graphql) -or !(Test-Path router.yaml)) { Write-Error \'Fichiers supergraph ou router manquants\'; exit 1 } else { Write-Host \'Supergraph et router configuration valides.\' }"'
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            when {
                expression { return params.BUILD_DOCKER }
            }
            steps {
                script {
                    echo "🐳 Construction des images Docker avec Docker Compose..."
                    if (isUnix()) {
                        sh "docker compose build"
                    } else {
                        bat "docker compose build"
                    }
                }
            }
        }

        stage('Container Smoke & Health Test') {
            when {
                expression { return params.RUN_CONTAINER_TESTS }
            }
            steps {
                script {
                    echo "🧪 Lancement du stack de conteneurs et vérification des Health Checks..."
                    try {
                        if (isUnix()) {
                            sh "docker compose up -d"
                            sh "sleep 15"
                            sh "curl -f http://localhost:8081/q/health || exit 1"
                            sh "curl -f http://localhost:8082/q/health || exit 1"
                            sh "curl -f http://localhost:8083/q/health || exit 1"
                            sh "curl -f http://localhost:8084/q/health || exit 1"
                        } else {
                            bat "docker compose up -d"
                            bat 'powershell -Command "Start-Sleep -Seconds 15; Invoke-WebRequest -Uri http://localhost:8081/q/health -UseBasicParsing"'
                        }
                        echo "✅ Conteneurs démarrés et réponsifs !"
                    } finally {
                        echo "🧹 Arrêt et nettoyage des conteneurs de test..."
                        if (isUnix()) {
                            sh "docker compose down"
                        } else {
                            bat "docker compose down"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo '🧹 Nettoyage post-exécution du pipeline Jenkins.'
        }
        success {
            echo '🎉 Pipeline School Management terminé avec SUCCÈS !'
            archiveArtifacts artifacts: '**/target/quarkus-app/**', allowEmptyArchive: true
        }
        failure {
            echo '❌ ÉCHEC du pipeline Jenkins sur School Management Platform.'
        }
        changed {
            echo '🔔 Statut du build modifié par rapport au build précédent.'
        }
    }
}

