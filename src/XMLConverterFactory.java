
public class XMLConverterFactory
{
    public static final String GEDCOM_CONVERTER = "gedcom";
    public IXMLConverter getConverter(String converterType)
    {
        if(converterType == null)
            return null;

        if(converterType.equalsIgnoreCase(GEDCOM_CONVERTER))
        {
            return new Gedcom2XMLImpl();
        }
        return null;
    }
}
