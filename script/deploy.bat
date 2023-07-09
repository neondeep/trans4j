@echo off
cd %~dp0 && cd ../

call mvn clean deploy -DskipTests
