package com.richard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class StringAccumulatorTest {

    @Before
    public void setup() {

    }

    @Test
    public void testGetDelimiterDefault() {
       assertEquals(new DelimiterResult(",",0), StringAccumulator.getDelimiter(""));
    }

    @Test
    public void testGetDelimiterNotDefault() {
      assertEquals(new DelimiterResult("a|b",6), StringAccumulator.getDelimiter("//a|b\n1a2b"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetDelimiterNotDefaultInvalid() {
       StringAccumulator.getDelimiter("//a|b");
    }

    @Test
    public void testTokenParserIsNumber() {
       String str1 = "1234";
       assertTrue(TokenParser.isNumber(str1,0,str1.length()));
       String str2 = "ab1234ef";
       assertFalse(TokenParser.isNumber(str2,0,str2.length()));
       assertTrue(TokenParser.isNumber(str2,2,6));
    }

    @Test
    public void testTokenParserParseOk() {
       String str1 = "1234";
       assertEquals(1234, TokenParser.parseInt(str1,0,str1.length(),9999));
       String str2 = "ab1234ef";
       assertEquals(1234, TokenParser.parseInt(str2,2,6,9999));
    }
  
    @Test(expected=NumberTooLargeException.class) 
    public void testTokenParserParseTooLarge() {
       String str1 = "1234";
       TokenParser.parseInt(str1,0,str1.length(),999);
    }

    @Test(expected=NotANumberException.class)
    public void testTokenParseNotANumber() {
       String str2 = "ab1234ef";
       assertEquals(1234, TokenParser.parseInt(str2,0,str2.length(),9999));
    }

    @Test
    public void testTokenParserPrimaryParseOk() {
       String str1 = "123";
       assertEquals(223, TokenParser.parse(100, str1, 0, str1.length(), (x,y) -> {return x+y;} , 999));
    }

    @Test(expected=NegativeNumberException.class)
    public void testTokenParserPrimaryNegativeNo() {
       String str1 = "-123";
       TokenParser.parse(100, str1, 0, str1.length(), (x,y) -> {return x+y;}, 999);
    }

    @Test(expected=IgnoreTextException.class)
    public void testTokenParserPrimaryIgnoreLargeNo() {
       String str1 = "3333";
       TokenParser.parse(100, str1, 0, str1.length(), (x,y) -> {return x+y;}, 999);
    }

    @Test(expected=IgnoreTextException.class)
    public void testTokenParserPrimaryIgnoreText() {
       String str1 = "12a4";
       TokenParser.parse(100, str1, 0, str1.length(), (x,y) -> {return x+y;}, 999); 
    }
   
     
    @Test
    public void testAddDefaultDelimiters() {
       assertEquals(8,StringAccumulator.simpleAdd("1,3,4"));
       //TODO add performantAdd
       assertEquals(10,StringAccumulator.simpleAdd("1,4\n5"));
       //TODO add performantAdd
       assertEquals(8,StringAccumulator.simpleAdd("1,1000,3,4"));
       //TODO add performantAdd
       StringBuilder strB = new StringBuilder();
       for (int i = 0; i < 10000; i++) {
            if (i == 9999) strB.append("1");
            else strB.append("1,");
       }
       long beforesimpleadd = System.currentTimeMillis();
       assertEquals(10000,StringAccumulator.simpleAdd(strB.toString()));
       long aftersimpleadd = System.currentTimeMillis();
       System.err.printf("Simple Add 10000 Default Delimiter before: %d after: %d Time in millis: %d\n", beforesimpleadd, aftersimpleadd, aftersimpleadd - beforesimpleadd);
    }
    
    @Test
    public void testAddNonDefaultDelimiters() {
       assertEquals(13,StringAccumulator.simpleAdd("//a\n1a3\n4a5"));
       //TODO add performantAdd
       assertEquals(15,StringAccumulator.simpleAdd("//a|--|&&&\n1a2--3\n4&&&5"));
       //TODO add performantAdd
       assertEquals(13,StringAccumulator.simpleAdd("//a\n1a3\n1000\n4a5"));
       //TODO add performantAdd

       // with failure, temporarily commented out
       StringBuilder strB = new StringBuilder();
       strB.append("//---\n");
       for (int i = 0; i < 9999; i++) {
          if (i == 0) strB.append("1");
          strB.append("---1");
       }
       //System.out.printf("--------\n%s\n------------\n",strB.toString());
       long beforesimpleadd = System.currentTimeMillis();
       assertEquals(10000,StringAccumulator.simpleAdd(strB.toString()));
       long aftersimpleadd = System.currentTimeMillis();
       System.err.printf("Simple Add 10000 Delimiter --- before:%d after:%d Time in Millis:%d\n", beforesimpleadd, aftersimpleadd, aftersimpleadd - beforesimpleadd);
       //TODO add performantAdd 
        
    }

    @Test
    public void testAddNegativeNos() {
       String str1 = "1,-2,3,-4";
       try {
           StringAccumulator.simpleAdd(str1);
           fail("should be with exception");
       } catch (Exception e) {
           String errMsg = e.getMessage();
           System.err.printf("%s gives err msg: %s\n",str1,errMsg);
           assertTrue(e instanceof NegativeNumberException);
           assertTrue(errMsg.contains("-2"));
           assertTrue(errMsg.contains("-4"));
       }
       //TODO add performantAdd 

       String str2 = "1,1000,-2,-3,2000,4";
       try {
           StringAccumulator.simpleAdd(str2);
           fail("should be with exception");
       } catch (Exception e) {
           String errMsg = e.getMessage();
           System.err.printf("%s gives err msg: %s\n",str2,errMsg);
           assertTrue(e instanceof NegativeNumberException);
           assertTrue(errMsg.contains("-2"));
           assertTrue(errMsg.contains("-3"));
       }
       //TODO add performantAdd
       
    }
}
