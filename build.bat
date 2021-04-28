@ECHO OFF
REM builds application

cd scripts

CALL exportDecoLib.bat
CALL buildApp.bat

ECHO Application built!
