
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gedcom2XMLTest
{	
	public static void main(String[] args) throws IOException, Exception
	{
		if(args.length < 2)
		{
			System.out.println("Usage: java Gedcom2XMLTest <gedcom_inputfilename> <xml_outputfilename>");
			System.exit(0);
		}

		Gedcom2XMLFile gedcom2xml = new Gedcom2XMLFile(args[0],args[1]);
		gedcom2xml.convert();
	}
}
