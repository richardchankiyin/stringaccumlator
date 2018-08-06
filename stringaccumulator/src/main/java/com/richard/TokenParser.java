package com.richard;

import java.util.function.BiFunction;

public class TokenParser {
    public static int parse(int initAmt, CharSequence chars, int start, int end, BiFunction<Integer,Integer,Integer> func, int max) {
        int thisno = 0;
        boolean negsignfound = false;
        negsignfound = chars.charAt(start) == '-';
        // check negative sign found and a potential negative no
        if (negsignfound) {
             if (isNumber(chars, start+1, end))
                  throw new NegativeNumberException();
             else
                  throw new IgnoreTextException();   
        }
         
        // check number and it is a no less than or equals to 100, if not ignore it
        try {
             thisno = parseInt(chars, start, end, max);
        } 
        catch (Exception e) { throw new IgnoreTextException(e); }
        return func.apply(initAmt,thisno);    
    } 
    private static boolean isDigit(char input) {
         int v = (int)input;
         return v >= 48 && v <= 57;
    }

    private static int char2Int(char input) {
         int v = (int)input;
         v -= 48;
         if (v >= 0 && v <= 9) { return v; }
         else { throw new NotANumberException(); }
    }


    /**
     * This method just checks whether all are digits. Negative signs
     * are not being accepted 
     */
    public static boolean isNumber(CharSequence chars, int start, int end) {
       boolean result = true;

       for (int i = start; result && i < end; i++) {
          result &= isDigit(chars.charAt(i));
       }

       return result;
    }


    /**
     * This is a method to check whether input is a number
     * If this is a number, we will parse as int and compare
     * with the max. If the no is larger than max, throw exception 
     */ 
    static int parseInt(CharSequence chars, int start, int end, int max) {
       int result = 0;
       boolean isDigit = true;
       for (int i = start; isDigit && i < end; i++) {
          char c = chars.charAt(i);
          isDigit &= isDigit(c);
          int power = end - 1 - i;
          result += Math.pow(10, power) * char2Int(c);
       }

       if (!isDigit)
          throw new NotANumberException(); 
       if (result > max)
          throw new NumberTooLargeException();

       return result;  
    }
}

class NegativeNumberException extends RuntimeException {
   public NegativeNumberException() {}
   public NegativeNumberException(String msg) { super(msg); }
}
class IgnoreTextException extends RuntimeException {
   public IgnoreTextException() {};
   public IgnoreTextException(Throwable t) { super(t); }
}
class NumberTooLargeException extends RuntimeException {}
class NotANumberException extends RuntimeException {}
