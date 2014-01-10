
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class GedcomLineParserTest {

    @Test
    public void testInvalidCases() {
        GedcomLineParser parser = new GedcomLineParser();
        assertFalse(parser.parse(""));
        assertFalse(parser.parse("\t\t"));
        assertFalse(parser.parse("TAG"));
        assertFalse(parser.parse("   TAG   "));
        assertFalse(parser.parse("1TAG"));
        assertFalse(parser.parse("   1TAG   "));
        assertTrue(parser.parse("-2 TAG"));
        assertFalse(parser.parse("TAG 3"));
        assertFalse(parser.parse("4 @I001@  "));
    }

    @Test
    public void testValidCases() {
        GedcomLineParser parser = new GedcomLineParser();
        assertTrue(parser.parse("1  ABC  DEF"));
        assertFalse(parser.HasId());
        assertEquals(parser.getLevel(),1);
        assertEquals(parser.getTag(),"abc");
        assertEquals(parser.getValue(),"DEF");

        assertTrue(parser.parse("    1  ABC       DEF       "));
        assertFalse(parser.HasId());
        assertEquals(parser.getLevel(),1);
        assertEquals(parser.getTag(),"abc");
        assertEquals(parser.getValue(),"DEF");

        assertTrue(parser.parse("2    TAG    "));
        assertFalse(parser.HasId());
        assertEquals(parser.getLevel(),2);
        assertEquals(parser.getTag(),"tag");
        assertEquals(parser.getValue(),"");

        assertTrue(parser.parse("3 TAG @I001@"));
        assertTrue(parser.HasId());
        assertEquals(parser.getLevel(),3);
        assertEquals(parser.getTag(),"tag");
        assertEquals(parser.getValue(),"@I001@");

        assertTrue(parser.parse("4 @I001@ TAG"));
        assertTrue(parser.HasId());
        assertEquals(parser.getLevel(),4);
        assertEquals(parser.getTag(),"tag");
        assertEquals(parser.getValue(),"@I001@");

        assertTrue(parser.parse("   1 NAME Elizabeth Alexandra Mary /Windsor/   "));
        assertFalse(parser.HasId());
        assertEquals(parser.getLevel(),1);
        assertEquals(parser.getTag(),"name");
        assertEquals("Elizabeth Alexandra Mary /Windsor/", parser.getValue());
    }
}
