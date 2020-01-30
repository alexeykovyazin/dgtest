@echo off
setlocal enabledelayedexpansion enableextensions
set run_cmd=C:\HQbird\Firebird30\isql -i C:\dgtest\src\test\resurces\Scripts\selectTable.sql
cmd /c !run_cmd! 1>C:\dgtest\src\test\resurces\Logs\selectTable.log 2>&1
Exit