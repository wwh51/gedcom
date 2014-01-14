
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Gedcom2XMLImpl implements IXMLConverter
{
	private static final String ATTR_ID = "id";
	private static final String ATTR_VALUE = "value";

	public void convert(BufferedReader gedcom_input, Element rootElement ) throws IOException, InvalidFormatException
	{
		Stack<Element> elementStack = new Stack<Element>();
		Stack<Integer> elementLevel = new Stack<Integer>();
		elementStack.push(rootElement);
		elementLevel.push(-1); // as the root level
		GedcomLineParser parser = new GedcomLineParser();
		while (true)
		{
			String strLine = gedcom_input.readLine(); //IOException
			if ( strLine  == null )
				break;
			strLine = strLine.replaceAll(System.getProperty("line.separator"), "").trim();
			if(strLine.length() == 0)
				continue;

			if(!parser.parse(strLine))
			{
				throw new InvalidFormatException("Invalid gedcom format:[" + strLine + "]");
			}

			Element newNode = rootElement.getOwnerDocument().createElement(parser.getTag());
			if(parser.getValue() != null)
			{
				if(parser.HasId())
					newNode.setAttribute(ATTR_ID, parser.getValue());
				else
					newNode.setTextContent(parser.getValue());
			}
			// the previous node has child, so we need to set 'value' attribute of it
			if(parser.getLevel() > elementLevel.lastElement())
			{
				Element lastElement = elementStack.lastElement();
				String strText = lastElement.getTextContent();
				if(strText != null && strText.length() > 0)
				{
					for(int i = lastElement.getChildNodes().getLength() - 1; i >= 0; i--)
						lastElement.removeChild(lastElement.getChildNodes().item(i));
					lastElement.setAttribute(ATTR_VALUE, strText);
				}
			}

			// find the parent node of the new node
			while(parser.getLevel() <= elementLevel.lastElement())
			{
				elementLevel.pop();
				elementStack.pop();
			}
			elementStack.lastElement().appendChild(newNode);
			elementStack.push(newNode);
			elementLevel.push(parser.getLevel());
		}
	}

}
