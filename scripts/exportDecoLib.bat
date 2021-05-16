@ECHO OFF
REM Creates JAR file in /bin containing compiled decolib classes

CD ..
RMDIR /S /Q bin 
MKDIR bin
CD src

ECHO Compiling decolib classes...
javac -d ..\bin java\decolib\tissues\cell.java
javac -d ..\bin -cp ..\bin java\decolib\tissues\compartments.java
javac -d ..\bin -cp ..\bin java\decolib\tissues\compartments_init.java
javac -d ..\bin -cp ..\bin java\decolib\algorithm.java
javac -d ..\bin -cp ..\bin java\decolib\tracker.java
javac -d ..\bin -cp ..\bin java\decolib\planner.java

COPY java\decolib\README.md ..\bin\decolib
CD ..\bin
jar cf decolib.jar decolib
ECHO Done!
RMDIR /S /Q decolib

CD ../scripts
