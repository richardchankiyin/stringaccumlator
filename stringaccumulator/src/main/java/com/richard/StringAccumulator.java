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


    static String getDelimiter(String inputStr) {
        if (inputStr.startsWith(DELIMITER_DECLARE_START)) {
            int index = inputStr.indexOf(DELIMITER_DECLARE_END);
            if (index == -1) 
               throw new IllegalArgumentException("delimiter end string not found");
            return inputStr.substring(DELIMITER_DECLARE_START.length(), index);  
        } else {
            return DEFAULT_DELIMITER;
        }       
    }
}
