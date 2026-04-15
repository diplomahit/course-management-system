@echo off
echo ============================================
echo   Course Login System - Build & Deploy
echo ============================================
echo.

:: Configuration
set PROJECT_DIR=C:\Users\zhouw\.qclaw\workspace\course-project\backend
set TOMCAT_DIR=C:\Users\zhouw\apache-tomcat-10.1.52
set MAVEN_DIR=C:\Users\zhouw\apache-maven-3.9.9

:: Check Maven
if not exist "%MAVEN_DIR%\bin\mvn.cmd" (
    echo [ERROR] Maven not found at %MAVEN_DIR%
    echo Please install Maven first:
    echo   1. Download from https://maven.apache.org/download.cgi
    echo   2. Extract to C:\Users\zhouw\apache-maven-3.9.9
    echo   3. Or install via scoop: scoop install maven
    pause
    exit /b 1
)

:: Set environment
set JAVA_HOME=C:\Java\jdk-17
set MAVEN_HOME=%MAVEN_DIR%
set PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%

echo [Step 1] Compiling project...
cd /d %PROJECT_DIR%
call %MAVEN_DIR%\bin\mvn.cmd clean package -DskipTests

if not exist "%PROJECT_DIR%\target\login.war" (
    echo [ERROR] Build failed! Check the error messages above.
    pause
    exit /b 1
)

echo.
echo [Step 2] Stopping Tomcat...
taskkill /F /IM java.exe 2>nul
timeout /t 3 /nobreak >nul

echo.
echo [Step 3] Deploying to Tomcat...
copy /Y "%PROJECT_DIR%\target\login.war" "%TOMCAT_DIR%\webapps\"

echo.
echo [Step 4] Starting Tomcat...
set CATALINA_HOME=%TOMCAT_DIR%
call "%TOMCAT_DIR%\bin\startup.bat"

echo.
echo ============================================
echo   Deployment Complete!
echo ============================================
echo.
echo   Application URL: http://localhost:8080/login/
echo   API Health:      http://localhost:8080/login/api/health
echo.
echo   Test Account:
echo     Username: admin
echo     Password: 123456
echo.
pause