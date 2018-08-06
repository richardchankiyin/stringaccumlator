package com.richard;

import java.util.List;
import java.util.LinkedList;

/**
 * This is the class to support
 * parsing of string and sum of
 * integer
 */
public class StringAccumulator 
{
    private static final String DELIMITER_DECLARE_START = "//";
    private static final String DELIMITER_DECLARE_END = "\n";
    private static final String DEFAULT_DELIMITER = ",";
    private static final String ALWAYS_AS_DELIMITER = "\n";
    private static final int MAX_VALUE = 999;

    public static void main( String[] args )
    {
        System.out.println( "To be implemented!" );
    }

    static String generateNegativeNumbersMsg(List<String> strList) {
        return String.format("negatives now allowed. %s", strList);
    }

    /**
     * This method will make use of regex to split the items. When
     * a new item is being determined, summation will be done immediately
     * which does not involve another transverse after splitting
     */
    public static int performantAdd(String inputStr) {
        //TODO to be implemented
        return -1;
    }

    /**
     * This method is to use regex to split the string to retrieve
     * the items. Then traverse through the item one by one and perform
     * summation. Performance will degraded if the input string getting
     * very long
     */
    public static int simpleAdd(String inputStr) {
        DelimiterResult delimiterResult = getDelimiter(inputStr);
        int startPos = delimiterResult.getStringContentPos();
        String delimiterStr = delimiterResult.getDelimiter();
        delimiterStr += "|\n";
        String content = inputStr.substring(startPos);
        String[] items = content.split(delimiterStr);
        //System.err.printf("no of items: %d delimiter: %s\n", items.length, delimiterStr); 
        int result = 0;
        boolean isExceptionThrown = false;
        List<String> negativeNumberStrings = new LinkedList<String>();
        for (String item: items) {
           try {
                int interim = TokenParser.parse(result, item, 0, item.length(), (x,y)->{return x+y;},MAX_VALUE);
                //System.err.printf("noofitems: %d inputstr: %s item: %s result:%d interim: %d\n", items.length, inputStr.length(), item, result, interim);
                result = interim;
           } catch (IgnoreTextException ite) {
           } catch (NegativeNumberException nne) {
                isExceptionThrown = true;
                negativeNumberStrings.add(item); 
           }
        } 
        if (isExceptionThrown) {
            throw new NegativeNumberException(generateNegativeNumbersMsg(negativeNumberStrings));
        } 

        return result; 
    }

    static DelimiterResult getDelimiter(String inputStr) {
        if (inputStr.startsWith(DELIMITER_DECLARE_START)) {
            int index = inputStr.indexOf(DELIMITER_DECLARE_END);
            if (index == -1) 
               throw new IllegalArgumentException("delimiter end string not found");
            return new DelimiterResult(inputStr.substring(DELIMITER_DECLARE_START.length(), index), index + DELIMITER_DECLARE_END.length());  
        } else {
            return new DelimiterResult(DEFAULT_DELIMITER,0);
        }       
    }
}
