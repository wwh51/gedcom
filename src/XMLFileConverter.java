
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

public class XMLFileConverter
{
	private String m_strInputFileName;
	private String m_strOutputFileName;
	private String m_strRootTag;
	private IXMLConverter converter;

	public XMLFileConverter(String strInputFilename, String strOutputFilename
		, String strRootTag, IXMLConverter content_converter)
	{
		m_strInputFileName = strInputFilename;
		m_strOutputFileName = strOutputFilename;
		converter = content_converter;
		m_strRootTag = strRootTag;
	}


	public void convert() throws IOException, InvalidFormatException
	{
		BufferedReader filereader = new BufferedReader(new FileReader(m_strInputFileName));

		Document doc = CreateXMLDocObject();
		if(doc == null)
			return;
		Element rootElement = doc.createElement(m_strRootTag);
		doc.appendChild(rootElement);

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
	 		// TODO process exception?
   		}
   		return null;
	}

	private void SaveXMLDocument(Document doc, String strOutputFilename) throws IOException
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
	 		//tfe.printStackTrace();
	 		throw new IOException(tfe.toString());
   		}
	}

}
