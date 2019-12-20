@echo off
java -Denv=local -jar %~dp0\target\springcloud2-0.0.1-SNAPSHOT.jar
rem  -Dapollo.bootstrap.enabled=false 