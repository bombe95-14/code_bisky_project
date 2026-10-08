# Script PowerShell pour arrêter les microservices Quarkus et la passerelle Apollo
Write-Host "Arrêt des services sur les ports 4000, 8081, 8082, 8083, 8084..." -ForegroundColor Yellow

$ports = @(4000, 8081, 8082, 8083, 8084)

foreach ($port in $ports) {
    $connections = Get-NetTCPConnection -LocalPort $port -ErrorAction SilentlyContinue
    if ($connections) {
        foreach ($conn in $connections) {
            $pidToKill = $conn.OwningProcess
            if ($pidToKill -gt 0) {
                Stop-Process -Id $pidToKill -Force -ErrorAction SilentlyContinue
                Write-Host "  [+] Processus $pidToKill sur le port $port arrêté." -ForegroundColor Green
            }
        }
    } else {
        Write-Host "  [-] Aucun processus actif sur le port $port." -ForegroundColor Gray
    }
}
Write-Host "Tous les microservices et la passerelle Apollo ont été arrêtés." -ForegroundColor Green
