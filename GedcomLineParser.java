import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GedcomLineParser
{
	private static final Pattern gedcomPattern = Pattern.compile("\\s*(\\d+)\\s+(\\S+)\\s*(.*)", Pattern.DOTALL);
	private String m_strTag;
        	private String m_strValue;
       	private Boolean m_bHasId;
       	private int m_nLevel;

       	public GedcomLineParser()
        	{
        		Init();
        	}

	public Boolean parse(String strLine)
	{
		Init();
		Matcher m = gedcomPattern.matcher(strLine);
		if(!m.find())
			return false;

		m_strTag = m.group(2);
		m_strValue = m.group(3).trim();
		if(m_strValue.startsWith("@") && m_strValue.endsWith("@") && m_strValue.length() > 2)
		{
		        m_bHasId = true;
		}
		else if(m_strTag.startsWith("@") && m_strTag.endsWith("@") && m_strTag.length() > 2)
		{
		        if(m_strValue.length() <= 0)
		        		return false;

		        m_bHasId = true;
		        m_strTag = m_strValue;
		        m_strValue = m.group(2);
		}
		m_strTag = m_strTag.toLowerCase();
		m_nLevel = Integer.parseInt(m.group(1));
		return true;
	}

	public Boolean HasId()
	{
		return m_bHasId;
	}

	public int getLevel()
	{
		return m_nLevel;
	}

	public String getTag()
	{
		return m_strTag;
	}

	public String getValue()
	{
		return m_strValue;
	}

	private void Init()
	{
		m_strTag = m_strValue = null;
		m_bHasId = false;
	}
}
