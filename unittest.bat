javac -cp "./bin" -sourcepath "./src/" -d "./bin" src/*.java
javac -cp "./bin;./bin/junit.jar;./bin/xmlunit.jar;" -sourcepath "./test;./src" -d "./bin" test/*.java
java -cp "./bin/junit.jar;./bin/xmlunit.jar;./bin" org.junit.runner.JUnitCore GedcomLineParserTest
java -cp "./bin/junit.jar;./bin/xmlunit.jar;./bin" org.junit.runner.JUnitCore Gedcom2XMLImplTest
