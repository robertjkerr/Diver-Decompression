## Decompression Planner

WIP Java/Electron scuba diver decompression planner. Decompression library simulates tissue loading and predicts ascent profile and no decompression limit using Buhlmann ZHL-16C GF algorithm.

ZHL-16C half-times, A and B values for both inert gases are contained in src/DecoLib/tissues/compartments_init.java.

## Major dependencies

 - Java
 - Node.js
 - node-gyp (installed globally) [requires C++ build tools (Visual C++ on windows) & Python (see https://github.com/nodejs/node-gyp)]

## Build instructions

`scripts/exportDecoLib.bat` compiles and compresses Java backend into `/bin`.

`npm install` to install local Node.js dependencies.

`npm run make` builds the application.

Build is located in `/out` folder as specified by electron-forge.
