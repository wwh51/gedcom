
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

		Gedcom2XMLImpl converter = new Gedcom2XMLImpl();

		XMLFileConverter xml_converter = new XMLFileConverter(args[0],args[1], "gedcom", converter);
		xml_converter.convert();
	}
}
