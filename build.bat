@ECHO OFF
REM Builds both frontend and backend

cd scripts

CALL exportDecoLib.bat
CALL buildMods.bat

ECHO Modules built!
