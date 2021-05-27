#!/bin/bash
#A script to install and build npm modules 

cd ..
rm -rf node_modules

npm install
$(npm bin)/electron-rebuild
node node_modules/java/postInstall.js

cd scripts