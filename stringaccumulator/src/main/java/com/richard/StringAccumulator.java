package com.richard;

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
    public static void main( String[] args )
    {
        System.out.println( "To be implemented!" );
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
