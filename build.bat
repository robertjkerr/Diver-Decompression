@ECHO OFF
REM Builds both frontend and backend

CD scripts

CALL exportDecoLib.bat
CALL buildMods.bat

CD ..
ECHO Modules built!
