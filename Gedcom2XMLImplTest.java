
import java.io.IOException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.custommonkey.xmlunit.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class Gedcom2XMLImplTest {
    @Test
    public void testNormalCases() throws SAXException, IOException
    {
	    String strGedcom = "0 @I0001@ INDI\n";
	    strGedcom += "1 NAME Elizabeth Alexandra Mary /Windsor/";
	    String strGeneratedXML = GetXMLStringFromGedcomBuffer(strGedcom);
	    String strBaseXML = "<gedcom><indi id=\"@I0001@\"><name>Elizabeth Alexandra Mary /Windsor/</name></indi></gedcom>";		
		Diff xmlDiff = new Diff(strGeneratedXML, strBaseXML);
		assertTrue(xmlDiff.similar());
    }

    private String GetXMLStringFromGedcomBuffer(String strGedcom) throws SAXException, IOException
	{
		InputStream is = new ByteArrayInputStream(strGedcom.getBytes());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		Document doc = CreateXMLDocObject();
		if(doc == null)
			return "";

		Element rootElement = doc.createElement("gedcom");
		doc.appendChild(rootElement);
		Gedcom2XMLImpl converter = new Gedcom2XMLImpl();
		converter.convert(br, rootElement);
		return getStringFromDocument(doc);
	}

    private String getStringFromDocument(Document doc)
	{
	    try
	    {
	       DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer = tf.newTransformer();
	       transformer.transform(domSource, result);
	       return writer.toString();
	    }
	    catch(TransformerException ex)
	    {
	       ex.printStackTrace();
	       return null;
	    }
	}

	private Document CreateXMLDocObject()
	{
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			return doc;
		}
		catch (ParserConfigurationException pce) {
	 		pce.printStackTrace();
   		}
   		return null;
	}

}
