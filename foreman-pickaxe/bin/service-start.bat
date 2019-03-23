@echo off
CLS

:init
setlocal DisableDelayedExpansion
set "batchPath=%~0"
for %%k in (%0) do set batchName=%%~nk
set "vbsGetPrivileges=%temp%\OEgetPriv_%batchName%.vbs"
setlocal EnableDelayedExpansion

:checkPrivileges
NET FILE 1>NUL 2>NUL
if '%errorlevel%' == '0' ( goto gotPrivileges ) else ( goto getPrivileges )

:getPrivileges
if '%1'=='ELEV' (echo ELEV & shift /1 & goto gotPrivileges)

ECHO Set UAC = CreateObject^("Shell.Application"^) > "%vbsGetPrivileges%"
ECHO args = "ELEV " >> "%vbsGetPrivileges%"
ECHO For Each strArg in WScript.Arguments >> "%vbsGetPrivileges%"
ECHO args = args ^& strArg ^& " "  >> "%vbsGetPrivileges%"
ECHO Next >> "%vbsGetPrivileges%"
ECHO UAC.ShellExecute "!batchPath!", args, "", "runas", 1 >> "%vbsGetPrivileges%"
"%SystemRoot%\System32\WScript.exe" "%vbsGetPrivileges%" %*
exit /B

:gotPrivileges
setlocal & pushd .
cd /d %~dp0
if '%1'=='ELEV' (del "%vbsGetPrivileges%" 1>nul 2>nul  &  shift /1)

::::::::::::::::::::::::::::
::START
::::::::::::::::::::::::::::
set "PICKAXE_HOME=%~dp0.."

if "%PROCESSOR_ARCHITECTURE%" == "AMD64" (
    set NSSM="%PICKAXE_HOME%\bin\support\win64\nssm.exe"
) else (
    set NSSM="%PICKAXE_HOME%\bin\support\win32\nssm.exe"
)

ECHO.
ECHO **************************************
ECHO Installing Pickaxe
ECHO **************************************
ECHO.
ECHO Installing and starting service...
%NSSM% install Pickaxe "%PICKAXE_HOME%\bin\pickaxe.bat" ""
%NSSM% set Pickaxe Description "Foreman Pickaxe"
%NSSM% set Pickaxe Start SERVICE_AUTO_START
%NSSM% start Pickaxe
pause