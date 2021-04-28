# Decompression Planner

WIP Java/Electron scuba diver decompression planner. Decompression library simulates tissue loading and predicts ascent profile and no decompression limit using Buhlmann ZHL-16C GF algorithm.

ZHL-16C half-times, A and B values for both inert gases are contained in src/DecoLib/tissues/compartments_init.java.

## Major dependencies

 - Java
 - Node.js and npm
 - MSVS Windows Build Tools and a Python2.7 installation (make sure to set your Python27 path and MSVS version correctly in you .npmrc)

## Windows build instructions

Execute `build.bat`, which compiles the java backend, and builds the node modules.

Execute `npm start` to start the app.