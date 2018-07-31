@echo off
setlocal enabledelayedexpansion
setlocal

rem # Set pickaxe home directory
set PICKAXE_HOME=%~dp0..

rem # Set java
if defined JAVA_HOME (
    set JAVA="%JAVA_HOME%\bin\java.exe"
) else (
    for %%I in (java.exe) do set JAVA="%%~$PATH:I"
)

if not exist %JAVA% (
    echo Failed to find java - set JAVA_HOME or add java to the PATH 1>&2
    exit /b 1
)

rem # Set JVM options
set JVM_OPTS_FILE="%PICKAXE_HOME%\conf\jvm.options"
for /F "usebackq delims=" %%a in (`findstr /b \- %JVM_OPTS_FILE%`) do set options=!options! %%a
set "JVM_OPTS=!options! %JVM_OPTS%"

rem # Set JVM classpath
for %%i in ("%PICKAXE_HOME%\lib\*.jar") do (
    call :concat "%%i"
)

rem # Set JVM parameters
set JVM_PARAMS=-Dlogback.configurationFile="%PICKAXE_HOME%\etc\logback.xml"
set JVM_PARAMS=%JVM_PARAMS% -DLOG_LOCATION="%PICKAXE_HOME%\logs"

rem # Set command line arguments
set JVM_COMMAND_LINE=-c "%PICKAXE_HOME%\conf\pickaxe.yml"

echo Starting pickaxe...
%JAVA% %JVM_OPTS% %JVM_PARAMS% -cp %CLASSPATH% mn.foreman.pickaxe.Main %JVM_COMMAND_LINE%
goto end

:concat
if not defined CLASSPATH (
    set CLASSPATH="%~1"
) else (
    set CLASSPATH=%CLASSPATH%;"%~1"
)
goto :eof

:end
endlocal