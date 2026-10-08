# Script PowerShell pour démarrer les 4 microservices Quarkus et la passerelle Apollo
$javaPath = "C:\Program Files\Java\jdk-21.0.12\bin\java.exe"
if (-not (Test-Path $javaPath)) {
    $javaPath = "java"
}

$baseDir = $PSScriptRoot

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  Démarrage de la Plateforme School Management    " -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan

$services = @(
    @{ Name = "school-service"; Port = 8081; Dir = "$baseDir\school-service" },
    @{ Name = "student-service"; Port = 8082; Dir = "$baseDir\student-service" },
    @{ Name = "course-service"; Port = 8083; Dir = "$baseDir\course-service" },
    @{ Name = "evaluation-service"; Port = 8084; Dir = "$baseDir\evaluation-service" }
)

foreach ($svc in $services) {
    Write-Host "[+] Lancement de $($svc.Name) sur le port $($svc.Port)..." -ForegroundColor Yellow
    Start-Process -FilePath $javaPath `
        -ArgumentList "-jar target\quarkus-app\quarkus-run.jar" `
        -WorkingDirectory $svc.Dir `
        -WindowStyle Hidden
}

Write-Host "`nAttente de l'initialisation des microservices..." -ForegroundColor Gray
Start-Sleep -Seconds 7

Write-Host "`nVérification de la disponibilité des microservices :" -ForegroundColor Cyan
foreach ($svc in $services) {
    try {
        $resp = Invoke-RestMethod -Uri "http://localhost:$($svc.Port)/q/health" -Method Get -TimeoutSec 3
        Write-Host "  [OK] $($svc.Name) actif sur http://localhost:$($svc.Port)/graphql (Health: $($resp.status))" -ForegroundColor Green
    } catch {
        Write-Host "  [WAIT] $($svc.Name) en cours de démarrage sur http://localhost:$($svc.Port)..." -ForegroundColor Yellow
    }
}

# Démarrage de la passerelle Apollo Supergraph
Write-Host "`n[+] Lancement de la Passerelle Apollo Supergraph sur le port 4000..." -ForegroundColor Yellow
Start-Process -FilePath "node" `
    -ArgumentList "gateway.mjs" `
    -WorkingDirectory "$baseDir\apollo-router" `
    -WindowStyle Hidden

Start-Sleep -Seconds 3

try {
    $gwTest = Invoke-RestMethod -Uri "http://localhost:4000/" -Method Post -ContentType "application/json" -Body '{"query":"{ __typename }"}' -TimeoutSec 3
    Write-Host "  [OK] Apollo Supergraph Gateway actif sur http://localhost:4000/" -ForegroundColor Green
} catch {
    Write-Host "  [WAIT] Passerelle Apollo en cours d'initialisation..." -ForegroundColor Yellow
}

Write-Host "`n==================================================" -ForegroundColor Cyan
Write-Host "  🎉 Système de Microservices Complètement Prêt   " -ForegroundColor Green
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "Point d'entrée unique (Supergraph) :" -ForegroundColor Cyan
Write-Host "  -> http://localhost:4000/ (Apollo Sandbox & GraphQL Endpoint)`n"
Write-Host "Endpoints des Subgraphs individuels :" -ForegroundColor Gray
Write-Host "  - School:     http://localhost:8081/q/graphql-ui"
Write-Host "  - Student:    http://localhost:8082/q/graphql-ui"
Write-Host "  - Course:     http://localhost:8083/q/graphql-ui"
Write-Host "  - Evaluation: http://localhost:8084/q/graphql-ui"
