@echo off
echo ============================================
echo   Maven Installation Script
echo ============================================
echo.

set MAVEN_VERSION=3.9.9
set MAVEN_URL=https://dlcdn.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip
set INSTALL_DIR=C:\Users\zhouw

echo Downloading Maven %MAVEN_VERSION%...
curl -L -o "%INSTALL_DIR%\maven.zip" "%MAVEN_URL%"

if not exist "%INSTALL_DIR%\maven.zip" (
    echo [ERROR] Download failed!
    pause
    exit /b 1
)

echo.
echo Extracting...
powershell -Command "Expand-Archive -Path '%INSTALL_DIR%\maven.zip' -DestinationPath '%INSTALL_DIR%' -Force"

echo.
echo Cleaning up...
del "%INSTALL_DIR%\maven.zip"

echo.
echo ============================================
echo   Maven installed successfully!
echo ============================================
echo.
echo   Location: %INSTALL_DIR%\apache-maven-%MAVEN_VERSION%
echo.
echo   Add to PATH:
echo   set MAVEN_HOME=%INSTALL_DIR%\apache-maven-%MAVEN_VERSION%
echo   set PATH=%%MAVEN_HOME%%\bin;%%PATH%%
echo.
pause