@echo off
cd C:\Users\zhouw\.qclaw\workspace\course-project\backend
call mvn clean package -DskipTests -q
echo Build completed!