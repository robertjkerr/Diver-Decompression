@ECHO OFF
REM A script to install and build npm modules 

cd ..

CALL npm install
CALL .\node_modules\.bin\electron-rebuild.cmd
node node_modules\java\postInstall.js

cd scripts

