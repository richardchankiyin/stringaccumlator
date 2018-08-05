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
       assertTrue(TokenParser.isNumber(str1.toCharArray(),0,str1.length()));
       String str2 = "ab1234ef";
       assertFalse(TokenParser.isNumber(str2.toCharArray(),0,str2.length()));
       assertTrue(TokenParser.isNumber(str2.toCharArray(),2,6));
    }
}
