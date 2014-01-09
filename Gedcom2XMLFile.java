
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Gedcom2XMLFile
{	
	private String m_strInputFileName;
	private String m_strOutputFileName;
	
	public Gedcom2XMLFile(String strInputFilename, String strOutputFilename)
	{
		m_strInputFileName = strInputFilename;
		m_strOutputFileName = strOutputFilename;
	}


	public void convert() throws IOException
	{
		BufferedReader filereader = new BufferedReader(new FileReader(m_strInputFileName));

		Document doc = CreateXMLDocObject();
		if(doc == null)
			return;
		Element rootElement = doc.createElement("gedcom");
		doc.appendChild(rootElement);

		Gedcom2XMLImpl converter = new Gedcom2XMLImpl();
		converter.convert(filereader, rootElement);		
		SaveXMLDocument(doc, m_strOutputFileName);
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

	private void SaveXMLDocument(Document doc, String strOutputFilename)
	{		
		try
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(strOutputFilename));
			transformer.transform(source, result);
		}
		catch (TransformerException tfe) {
	 		tfe.printStackTrace();
   		}
	}
	
}
