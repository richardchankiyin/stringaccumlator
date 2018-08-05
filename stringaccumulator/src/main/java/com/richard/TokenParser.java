package com.richard;

import java.util.function.BiFunction;

public class TokenParser {
    public static int parse(int initAmt, char[] chars, int start, int end, BiFunction<Integer,Integer,Integer> func) {
        int thisno = 0;
        boolean negsignfound = false;
        negsignfound = chars[start] == '-';
        // check negative sign found and a potential negative no
        if (negsignfound) {
             if (isNumber(chars, start+1, end))
                  throw new NegativeNumberException();
             else
                  throw new IgnoreTextException();   
        }
         
        //TODO to be implemented
        // check number and it is a three digit one, if not ignore it
        return func.apply(initAmt,thisno);    
    } 
    private static boolean isDigit(char input) {
      return input == '0' ||
      input == '1' ||
      input == '2' ||
      input == '3' ||
      input == '4' ||
      input == '5' ||
      input == '6' ||
      input == '7' ||
      input == '8' ||
      input == '9';
    }
    public static boolean isNumber(char[] chars, int start, int end) {
       boolean result = true;

       for (int i = start; result && i < end; i++) {
          result &= isDigit(chars[i]);
       }

       return result;
    }
}

class NegativeNumberException extends RuntimeException {}
class IgnoreTextException extends RuntimeException {}

