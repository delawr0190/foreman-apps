@echo off
setlocal

set PICKAXE_HOME=%~dp0..

if "%PROCESSOR_ARCHITECTURE%" == "AMD64" (
    set NSSM="%PICKAXE_HOME%\bin\support\win64\nssm.exe"
) else (
    set NSSM="%PICKAXE_HOME%\bin\support\win32\nssm.exe"
)

%NSSM% install Pickaxe "%PICKAXE_HOME%\bin\pickaxe.bat" ""
%NSSM% set Pickaxe Description "Foreman Pickaxe"
%NSSM% set Pickaxe Start SERVICE_AUTO_START