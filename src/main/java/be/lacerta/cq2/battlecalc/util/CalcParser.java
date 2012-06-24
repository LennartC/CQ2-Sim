package be.lacerta.cq2.battlecalc.util;

public class CalcParser {

	  private CalcTokenizer myTokenizer;
	  private double result;


	  public CalcParser() {
	    myTokenizer = new CalcTokenizer();
	  }
	  
	  public double calc(String s) throws IllegalArgumentException {
	  	myTokenizer.tokenize(s);
	  	result = parseExpression();
	  	match(CalcTokenizer.EOLN_TOKEN);
	  	return result;
	  }

	  private void match(int token) throws IllegalArgumentException {
	    if (myTokenizer.nextToken() != token) {
	      if (token == CalcTokenizer.EOLN_TOKEN)
	      	throw new IllegalArgumentException("Unexpected text after the expression.");
	      else if (token == CalcTokenizer.NUMBER_TOKEN) 
	      	throw new IllegalArgumentException("Expected a number.");
	      else throw new IllegalArgumentException("Expected a " + ((char) token) + ".");
	    }
	    myTokenizer.advance();
	  }

	  private double parseExpression() throws IllegalArgumentException {
	    double result = parseMulexp();

	    while (true) {
	      if (myTokenizer.nextToken() == '+') {
	        match('+');
	        result += parseMulexp();
	      }
	      else if (myTokenizer.nextToken() == '-') {
	        match('-');
	        result -= parseMulexp();
	      }
	      else return result;
	    }
	  }

	  private double parseMulexp() throws IllegalArgumentException {

	    double result = parseRootexp();

	    while (true) {
	      if (myTokenizer.nextToken() == '*') {
	        match('*');
	        result *= parseRootexp();
	      }
	      else if (myTokenizer.nextToken() == '/') {
	        match('/');
	        result /= parseRootexp();
	      }
	      else return result;
	    }
	  }

	  private double parseRootexp() throws IllegalArgumentException {
	    double result = 0.0;

	    // <rootexp> ::= '(' <expression> ')'

	    if (myTokenizer.nextToken() == '(') {
	      match('(');
	      result = parseExpression();
	      match(')');
	    }

	    // <rootexp> ::= number

	    else if (myTokenizer.nextToken()==CalcTokenizer.NUMBER_TOKEN){
	      result = myTokenizer.getNum();
	      match(CalcTokenizer.NUMBER_TOKEN);
	    }

	    else {
	    	throw new IllegalArgumentException("Expected a number or a parenthesis.");
	    }

	    return result;
	  }

	}