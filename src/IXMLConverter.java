
import java.lang.*;
import java.io.IOException;
import java.io.BufferedReader;
import org.w3c.dom.Element;

public interface IXMLConverter
{
	public void convert(BufferedReader gedcom_input, Element rootElement ) throws IOException, InvalidFormatException;
}
