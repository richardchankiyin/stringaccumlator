package com.richard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
}
