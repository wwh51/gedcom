
public class GedcomLineParser
{	
	protected String m_strLevel;
	protected String m_strTag;
	protected String m_strValue;
	protected Boolean m_bValueIsId;

	public GedcomLineParser(String strLine)
	{
		m_strTag = m_strValue = m_strLevel = null;
		m_bValueIsId = false;
		if (strLine != null )
		{
			DoParse(strLine.trim());
		}
	}
	
	protected void DoParse(String strLine)
	{
		String[] tokens = strLine.split("\\s+", 3);
		if(tokens.length < 1)
			return;
		m_strLevel = tokens[0];
		if(tokens.length < 2)
			return;		
		m_strTag = tokens[1].toLowerCase();
		if(tokens.length < 3)
			return;
		m_strValue = tokens[2];		
		
		if(m_strValue.startsWith("@") && m_strValue.endsWith("@"))
		{
			m_bValueIsId = true;			
		}
		else if(m_strTag.startsWith("@") && m_strTag.endsWith("@"))
		{
			m_bValueIsId = true;
			m_strValue = tokens[1];
			m_strTag = tokens[2].toLowerCase();
			return;			
		}
	}
	
	public Boolean IsValid()
	{
		return m_strLevel != null && m_strTag != null;
	}

	public Boolean IsIDValue()
	{
		return m_bValueIsId;
	}

	public String getStrLevel()
	{
		return m_strLevel;
	}

	public int getIntLevel()
	{		
		return Integer.parseInt(m_strLevel);
	}

	public String getTag()
	{
		return m_strTag;
	}

	public String getValue()
	{
		return m_strValue;
	}
}
