# Decompression Planner

## DISCLAIMER

DO NOT use this software to plan real dives and determine safe and/or accurate decompression/no-decompression times. This project is being developed for learning and for fun. It should not be applied in any real use case, and certainly not in a case where somebody's life depends on it.

### Description

WIP Java/Electron scuba diver decompression planner. Decompression library simulates tissue loading and predicts ascent profile and no decompression limit using Buhlmann ZHL-16C GF algorithm.

ZHL-16C half-times, A and B values for both inert gases are contained in `src/DecoLib/tissues/compartments_init.java`.

### Major dependencies

 - Java
 - Node.js and npm
 - node-gyp 
 - (Windows only alternative to node-gyp) MSVS Windows Build Tools and a Python2.7 installation (make sure to set your Python2.7 path and MSVS version correctly in your .npmrc)

### Build instructions

Execute `.\build.cmd` or `./build.sh` on Windows (cmd) and *nix (bash) respectively, which compiles the Java backend, and builds the Nodejs modules.

Execute `npm start` to start the app.
