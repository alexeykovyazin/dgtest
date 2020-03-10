@echo off
set path=c:\dgtest\Maven\bin;%javaPath%;%path%
pushd %~dp0
echo.CD=%CD%
<config.txt (for /f "delims=" %%a in ('more') do @set "%%a") 2>nul
cd C:\dgtest\
mvn clean test -Dbrowsername=%brosername% -Dpathhqbirddata=%pathhqbirddata% -DserverInstallationPath=%serverInstallationPath%
pause