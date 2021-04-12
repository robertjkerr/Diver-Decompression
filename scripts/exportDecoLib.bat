@ECHO OFF
REM Creates JAR file in /bin containing compiled decolib classes

cd ..
rm -r bin
mkdir bin
cd src

ECHO Compiling decolib classes...
javac -d ../bin java/decolib/tissues/cell.java
javac -d ../bin -cp ../bin java/decolib/tissues/compartments.java
javac -d ../bin -cp ../bin java/decolib/tissues/compartments_init.java
javac -d ../bin -cp ../bin java/decolib/algorithm.java
javac -d ../bin -cp ../bin java/decolib/tracker.java
javac -d ../bin -cp ../bin java/decolib/planner.java

cp java/decolib/README.md ../bin/decolib
cd ../bin
jar cf decolib.jar decolib
rm -r decolib
