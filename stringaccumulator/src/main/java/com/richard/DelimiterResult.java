package com.richard;

public class DelimiterResult {
   private String delimiter = null;
   private int stringContentPos = 0;
   public DelimiterResult(String x, int y) {
      if (x == null) throw new IllegalArgumentException("delimiter should not be null!");
      this.delimiter = x;
      this.stringContentPos = y;

   }
   public String getDelimiter() { return this.delimiter; }
   public int getStringContentPos() { return this.stringContentPos; } 
   public boolean equals(Object o) {
      if (o instanceof DelimiterResult) {
         DelimiterResult or = (DelimiterResult)o;
         return this.delimiter.equals(or.getDelimiter()) && this.stringContentPos == or.getStringContentPos(); 
      } else  {
          return false;
      }
   }
}
