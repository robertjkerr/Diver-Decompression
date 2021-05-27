@ECHO OFF
REM Builds both frontend and backend

CD scripts

CALL exportDecoLib
CALL buildMods
CD ..
ECHO Modules built!
