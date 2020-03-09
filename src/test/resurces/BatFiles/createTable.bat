@echo off
setlocal enabledelayedexpansion enableextensions
pushd %~dp0
echo.CD=%CD%
<config.txt (for /f "delims=" %%a in ('more') do @set "%%a") 2>nul
Set serverBinary=%serverBinaryPath%
echo %serverBinary%
set run_cmd=%serverBinary%isql -i C:\dgtest\src\test\resurces\Scripts\createTable.sql
cmd /c !run_cmd! 1>C:\dgtest\src\test\resurces\Logs\createTable.log 2>&1
Exit