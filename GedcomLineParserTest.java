
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class GedcomLineParserTest {

    @Test
    public void testInvalidCases() {
    	GedcomLineParser parser = new GedcomLineParser("   ");
    	assertFalse(parser.IsValid());
    	parser = new GedcomLineParser("0 ");
    	assertFalse(parser.IsValid());
    }

    @Test
    public void testValidCases() {
    	GedcomLineParser parser = new GedcomLineParser("3 @I0001@ INDI");
    	assertTrue(parser.IsValid());
    	assertTrue(parser.IsIDValue());
    	assertEquals(parser.getStrLevel(),"3");
    	assertEquals(parser.getIntLevel(),3);
    	assertEquals(parser.getTag(),"indi");    	
    	assertEquals(parser.getValue(),"@I0001@");

    	parser = new GedcomLineParser("   2 PLAC 17 Bruton Street, London, W1   ");
    	assertTrue(parser.IsValid());
    	assertFalse(parser.IsIDValue());
    	assertEquals(parser.getStrLevel(),"2");
    	assertEquals(parser.getIntLevel(),2);
    	assertEquals(parser.getTag(),"plac");    	
    	assertEquals(parser.getValue(),"17 Bruton Street, London, W1");

    	parser = new GedcomLineParser("   1 FAMC @F0001@   ");
    	assertTrue(parser.IsValid());
    	assertTrue(parser.IsIDValue());
    	assertEquals(parser.getStrLevel(),"1");
    	assertEquals(parser.getIntLevel(),1);
    	assertEquals(parser.getTag(),"famc");    	
    	assertEquals(parser.getValue(),"@F0001@");
    }
}
