javac -cp "./bin" -sourcepath "./src/" -d "./bin" src/*.java
javac -cp "./bin;./bin/junit.jar;./bin/xmlunit.jar;" -sourcepath "./test;./src" -d "./bin" test/*.java
java -cp "./bin/junit.jar;./bin/xmlunit.jar;./bin" Gedcom2XMLTest ".\testfile\GEDCOM_Parser_Challenge_sample_data.txt" ".\testfile\GEDCOM_Parser_Challenge_sample_data.xml"
java -cp "./bin/junit.jar;./bin/xmlunit.jar;./bin" Gedcom2XMLTest ".\testfile\test_blanklines.txt" ".\testfile\test_blanklines.xml"
java -cp "./bin/junit.jar;./bin/xmlunit.jar;./bin" Gedcom2XMLTest ".\testfile\testsimple.txt" ".\testfile\testsimple.xml"
java -cp "./bin/junit.jar;./bin/xmlunit.jar;./bin" Gedcom2XMLTest ".\testfile\test_more.txt" ".\testfile\test_more.xml"
