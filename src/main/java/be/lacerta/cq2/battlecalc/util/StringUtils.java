package be.lacerta.cq2.battlecalc.util;

import java.util.StringTokenizer;

public class StringUtils {
  public static boolean isInteger(String s) {
        try {
          Integer.parseInt( s );
          return true;
        } catch( NumberFormatException ex ) { return false; }
  }

  public static int parseInt(String s) {
    if(s == null) return 0;
    int i = 0;
    try {
      i = Integer.parseInt(s.trim());
    }
    catch (NumberFormatException ex) {}
    return i;
  }

  public static String capitalize(String s) {
    if(s == null) return null;

    StringTokenizer tokenizer = new StringTokenizer(s, " ");
    String result = "";

    while (tokenizer.hasMoreTokens()) {
      StringBuffer token = new StringBuffer(tokenizer.nextToken());
      token.replace(0,1,token.substring(0,1).toUpperCase());
      if (!tokenizer.hasMoreTokens()) result += token;
      else result += token + " ";
    }
    return result;
  }

}
