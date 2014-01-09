
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Gedcom2XMLImpl
{	
	private static final String ATTR_ID = "id";
	private static final String ATTR_VALUE = "value";
	
	public Gedcom2XMLImpl()
	{		
	}

	public void convert(BufferedReader gedcom_input, Element rootElement ) throws IOException
	{		
		Stack<Element> elementStack = new Stack<Element>();
		Stack<Integer> elementLevel = new Stack<Integer>();
		elementStack.push(rootElement);
		elementLevel.push(-1);
		while (true) 
		{
			String strLine = gedcom_input.readLine();
			if ( strLine  == null )
				break;

			GedcomLineParser parser = new GedcomLineParser(strLine);
			if(!parser.IsValid())
				continue;

			Element newNode = rootElement.getOwnerDocument().createElement(parser.getTag());
			if(parser.getValue() != null)
			{
				if(parser.IsIDValue())
					newNode.setAttribute(ATTR_ID, parser.getValue());
				else
					newNode.setTextContent(parser.getValue());					
			}
			if(parser.getIntLevel() > elementLevel.lastElement())
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

			while(parser.getIntLevel() <= elementLevel.lastElement())
			{
				elementLevel.pop();
				elementStack.pop();
			}
			elementStack.lastElement().appendChild(newNode);
			elementStack.push(newNode);
			elementLevel.push(parser.getIntLevel());			
		}
	}
	
}
