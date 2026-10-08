@echo off
title School Management Platform - Launcher
echo =======================================================
echo   Lancement des 4 Microservices Quarkus et Apollo      
echo =======================================================

set JAVA_BIN="C:\Program Files\Java\jdk-21.0.12\bin\java.exe"

echo [+] Demarrage de school-service (port 8081)...
start "School Service (8081)" cmd /k "cd /d "%~dp0school-service" && %JAVA_BIN% -jar target\quarkus-app\quarkus-run.jar"

echo [+] Demarrage de student-service (port 8082)...
start "Student Service (8082)" cmd /k "cd /d "%~dp0student-service" && %JAVA_BIN% -jar target\quarkus-app\quarkus-run.jar"

echo [+] Demarrage de course-service (port 8083)...
start "Course Service (8083)" cmd /k "cd /d "%~dp0course-service" && %JAVA_BIN% -jar target\quarkus-app\quarkus-run.jar"

echo [+] Demarrage de evaluation-service (port 8084)...
start "Evaluation Service (8084)" cmd /k "cd /d "%~dp0evaluation-service" && %JAVA_BIN% -jar target\quarkus-app\quarkus-run.jar"

timeout /t 6 /nobreak >nul

echo [+] Demarrage de la passerelle Apollo Supergraph (port 4000)...
start "Apollo Gateway (4000)" cmd /k "cd /d "%~dp0apollo-router" && node gateway.mjs"

echo =======================================================
echo   Tous les services sont en cours d'execution !        
echo   Point d'entree : http://localhost:4000/              
echo =======================================================
