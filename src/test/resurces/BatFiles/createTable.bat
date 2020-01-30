@echo off
setlocal enabledelayedexpansion enableextensions
set run_cmd=C:\HQbird\Firebird30\isql -i C:\dgtest\src\test\resurces\Scripts\createTable.sql
cmd /c !run_cmd! 1>C:\dgtest\src\test\resurces\Logs\createTable.log 2>&1
Exit