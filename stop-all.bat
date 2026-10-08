@echo off
echo Arret de tous les microservices et de la passerelle Apollo...
powershell.exe -ExecutionPolicy Bypass -File "%~dp0stop-all.ps1"
pause
