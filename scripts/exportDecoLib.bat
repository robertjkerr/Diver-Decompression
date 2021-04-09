@ECHO OFF
REM Creates JAR file in /bin containing compiled DecoLib classes

cd ..
mkdir bin
cd src

javac -d ../bin DecoLib/tissues/cell.java
javac -d ../bin -cp ../bin DecoLib/tissues/compartments.java
javac -d ../bin -cp ../bin DecoLib/tissues/compartments_init.java
javac -d ../bin -cp ../bin DecoLib/algorithm.java
javac -d ../bin -cp ../bin DecoLib/tracker.java

cd ../bin
jar cf DecoLib.jar DecoLib
rm -r DecoLib
