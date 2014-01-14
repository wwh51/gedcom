1 why I selected 'GEDCOM Parser Challenge'
A: I'm interested in the file format of GEDCOM and I think it's a good sample to show my OOP skills.

2 Design and approach

First I developed a quick prototype with python (gedcom_parser.py). 

A class GedcomLineParser to parse one line of GEDCOM file is designed firstly. I used split("\\s+",  3) to parse it, then changed it to use regular expression. And I developed unit test codes (GedcomLineParserTest). Junit is used here.

Instead of creating one class to convert one GEDCOM file to XML file, I designed one interface IXMLConverter which has only one function:
convert(BufferedReader gedcom_input, Element rootElement )
Then I developed the class Gedcom2XMLImpl to implement a GEDCOM converter, and the class Gedcom2XMLImplTest to do unit test. Xmlunit is used to compare two similar xml trees.

XMLFileConverter is a general class which can convert any structured document to XML file.

At last, Gedcom2XMLTest a class to call XMLFileConverter and Gedcom2XMLImpl to do GECOM files converting.

The benefits of my design is: we can use Gedcom2XMLImpl for other places (GEDCOM data come from network or memory), not only do file converting. And XMLFileConverter can be used by other XML files converting.

3 How to execute the solution to the challenge
At gedcom/, run the following command
javac -cp "./junit.jar;./xmlunit.jar;./" *.java
java Gedcom2XMLTest ".\testfile\sample.txt" ".\testfile\sample.xml"

java Gedcom2XMLTest <gedcom_inputfilename> <xml_outputfilename> 

For unit test, please run
javac -cp "./junit.jar;./xmlunit.jar;./" *.java
java -cp "./junit.jar;./xmlunit.jar;./" org.junit.runner.JUnitCore GedcomLineParserTest
java -cp "./junit.jar;./xmlunit.jar;./" org.junit.runner.JUnitCore Gedcom2XMLImplTest

4 later improvement
TODO
