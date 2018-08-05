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
       assertEquals(",", StringAccumulator.getDelimiter(""));
    }

    @Test
    public void testGetDelimiterNotDefault() {
      assertEquals("a|b", StringAccumulator.getDelimiter("//a|b\n1a2b"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetDelimiterNotDefaultInvalid() {
       StringAccumulator.getDelimiter("//a|b");
    }
}
