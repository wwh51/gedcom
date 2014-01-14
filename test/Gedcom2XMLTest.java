
import java.io.IOException;

public class Gedcom2XMLTest
{
	public static void main(String[] args) throws IOException, Exception
	{
		if(args.length < 2)
		{
			System.out.println("Usage: java Gedcom2XMLTest <gedcom_inputfilename> <xml_outputfilename>");
			System.exit(0);
		}

		XMLConverterFactory converterFactory = new XMLConverterFactory();
		IXMLConverter converter = converterFactory.getConverter(XMLConverterFactory.GEDCOM_CONVERTER);
		if(converter == null)
		{
			System.out.println("Failed to create gedcom to xml converter.");
			System.exit(0);
		}
		XMLFileConverter file_converter = new XMLFileConverter(args[0],args[1], "gedcom", converter);
		file_converter.convert();
	}
}
