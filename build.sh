#!/bin/bash
#Builds both frontend and backend

cd scripts

bash ./exportDecoLib.sh
bash ./buildMods.sh

cd ..
echo "Modules built!"
