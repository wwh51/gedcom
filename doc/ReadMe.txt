1 why I selected 'GEDCOM Parser Challenge'
A: I'm interested in the file format of GEDCOM and I think it's a good sample to show my OOP skills.

2 Design and approach

First I developed a quick prototype with python (gedcom_parser.py). 

A class GedcomLineParser to parse one line of GEDCOM file is designed firstly. I used split("\\s+",  3) to parse it, then changed it to use regular expression. And I developed unit test codes (GedcomLineParserTest). JUnit is used here.

Instead of creating one class to convert one GEDCOM file to XML file, I designed one interface IXMLConverter which has only one function:
convert(BufferedReader gedcom_input, Element rootElement )
Then I developed the class Gedcom2XMLImpl to implement a GEDCOM converter, and the class Gedcom2XMLImplTest to do unit test. Xmlunit is used to compare two similar xml trees.
The factory design pattern (XMLConverterFactory.java) is used to create IXMLConverter object. 

XMLFileConverter is a general class which can convert any structured document to XML file.

At last, Gedcom2XMLTest a class to call XMLFileConverter and XMLConverterFactory to do GECOM files converting.

The benefits of my design is: we can use Gedcom2XMLImpl for other purposes (GEDCOM data come from network or memory), not to do file converting only. And XMLFileConverter can be used by other XML files converting.

3 How to execute the solution to the challenge

in Unix/Linux, chmod +x *.bat to make bat files executable

At ./, run the following command
unittest.bat # to run unit tests
make.bat # to built all java files and run command to convert gedcom files in testfiles/

4 Possible improvement
Now Gedcom2XMLTest throw Exception as it's test only and I want to get all details of exception. Maybe it should be changed to handle exception better.