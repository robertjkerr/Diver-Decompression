@ECHO OFF
REM A script to install and build npm modules 

CD ..
RMDIR /S /Q node_modules

CALL npm install
CALL .\node_modules\.bin\electron-rebuild.cmd
node node_modules\java\postInstall.js

CD scripts

