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
       assertEquals(0,StringAccumulator.simpleAdd(""));
       assertEquals(0,StringAccumulator.performantAdd(""));
       assertEquals(8,StringAccumulator.simpleAdd("1,3,4"));
       assertEquals(8,StringAccumulator.performantAdd("1,3,4"));
       assertEquals(10,StringAccumulator.simpleAdd("1,4\n5"));
       assertEquals(10,StringAccumulator.performantAdd("1,4\n5"));
       assertEquals(8,StringAccumulator.simpleAdd("1,1000,3,4"));
       StringBuilder strB = new StringBuilder();
       for (int i = 0; i < 100000; i++) {
            if (i == 99999) strB.append("1");
            else strB.append("1,");
       }
       long beforesimpleadd = System.currentTimeMillis();
       assertEquals(100000,StringAccumulator.simpleAdd(strB.toString()));
       long aftersimpleadd = System.currentTimeMillis();
       System.err.printf("Simple Add 100000 Default Delimiter before: %d after: %d Time in millis: %d\n", beforesimpleadd, aftersimpleadd, aftersimpleadd - beforesimpleadd);
       long beforeperformantadd = System.currentTimeMillis();
       assertEquals(100000,StringAccumulator.performantAdd(strB.toString())); 
       long afterperformantadd = System.currentTimeMillis();
       System.err.printf("Performant Add 100000 Default Delimiter before: %d after: %d Time in millis: %d\n", beforeperformantadd, afterperformantadd, afterperformantadd - beforeperformantadd);
    }
    
    @Test
    public void testAddNonDefaultDelimiters() {
       assertEquals(13,StringAccumulator.simpleAdd("//a\n1a3\n4a5"));
       assertEquals(13,StringAccumulator.performantAdd("//a\n1a3\n4a5"));
       assertEquals(15,StringAccumulator.simpleAdd("//a|--|&&&\n1a2--3\n4&&&5"));
       assertEquals(15,StringAccumulator.performantAdd("//a|--|&&&\n1a2--3\n4&&&5"));
       assertEquals(13,StringAccumulator.simpleAdd("//a\n1a3\n1000\n4a5"));
       assertEquals(13,StringAccumulator.performantAdd("//a\n1a3\n1000\n4a5"));

       StringBuilder strB = new StringBuilder();
       strB.append("//---\n");
       for (int i = 0; i < 99999; i++) {
          if (i == 0) strB.append("1");
          strB.append("---1");
       }
       long beforesimpleadd = System.currentTimeMillis();
       assertEquals(100000,StringAccumulator.simpleAdd(strB.toString()));
       long aftersimpleadd = System.currentTimeMillis();
       System.err.printf("Simple Add 100000 Delimiter --- before:%d after:%d Time in Millis:%d\n", beforesimpleadd, aftersimpleadd, aftersimpleadd - beforesimpleadd);
       long beforeperformantadd = System.currentTimeMillis();
       assertEquals(100000,StringAccumulator.performantAdd(strB.toString())); 
       long afterperformantadd = System.currentTimeMillis();
       System.err.printf("Performant Add 100000 Delimiter --- before:%d after:%d Time in Millis:%d\n", beforeperformantadd, afterperformantadd, afterperformantadd - beforeperformantadd);   
    }

    @Test
    public void testAddNegativeNos() {
       String str1 = "1,-2,3,-4";
       try {
           StringAccumulator.simpleAdd(str1);
           fail("should be with exception");
       } catch (Exception e) {
           String errMsg = e.getMessage();
           System.err.printf("SimpleAdd: %s gives err msg: %s\n",str1,errMsg);
           assertTrue(e instanceof NegativeNumberException);
           assertTrue(errMsg.contains("-2"));
           assertTrue(errMsg.contains("-4"));
       }
       try {
           StringAccumulator.performantAdd(str1);
           fail("should be with exception");
       } catch (Exception e) {
           String errMsg = e.getMessage();
           System.err.printf("PerformantAdd: %s gives err msg: %s\n",str1,errMsg);
           assertTrue(e instanceof NegativeNumberException);
           assertTrue(errMsg.contains("-2"));
           assertTrue(errMsg.contains("-4"));
       }
       
       String str2 = "1,1000,-2,-3,2000,4";
       try {
           StringAccumulator.simpleAdd(str2);
           fail("should be with exception");
       } catch (Exception e) {
           String errMsg = e.getMessage();
           System.err.printf("SimpleAdd: %s gives err msg: %s\n",str2,errMsg);
           assertTrue(e instanceof NegativeNumberException);
           assertTrue(errMsg.contains("-2"));
           assertTrue(errMsg.contains("-3"));
       }
       try {
           StringAccumulator.performantAdd(str2);
           fail("should be with exception");
       } catch (Exception e) {
           String errMsg = e.getMessage();
           System.err.printf("PerformantAdd: %s gives err msg: %s\n",str2,errMsg);
           assertTrue(e instanceof NegativeNumberException);
           assertTrue(errMsg.contains("-2"));
           assertTrue(errMsg.contains("-3"));
       }
 
    }
}
