@echo off
REM Windows batch script to run tests locally

echo Compiling Java files...
javac -cp .;lib/* *.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    exit /b 1
)

echo.
echo Running tests...
java -jar lib/junit-platform-console-standalone-6.0.1.jar execute --class-path .;lib/junit-jupiter-api-6.0.1.jar --scan-class-path 

pause
