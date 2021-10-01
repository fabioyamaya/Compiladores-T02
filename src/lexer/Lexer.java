package lexer;

import java.util.Hashtable;

import ast.CompilerError;

public class Lexer {
	
	public Lexer( char []input, CompilerError error ) {
        this.input = input;
          // add an end-of-file label to make it easy to do the lexer
        input[input.length - 1] = '\0';
          // number of the current line
        lineNumber = 1;
        tokenPos = 0;
        this.error = error;
    }
	
	// contains the keywords
    static private Hashtable<String, Symbol> keywordsTable;
	
	// this code will be executed only once for each program execution
    static {
       keywordsTable = new Hashtable<String, Symbol>();
       keywordsTable.put( "var", Symbol.VAR );
       keywordsTable.put( "if", Symbol.IF );
       keywordsTable.put( "else", Symbol.ELSE );
       keywordsTable.put( "Int", Symbol.INTEGER );
       keywordsTable.put( "while", Symbol.WHILE );
       keywordsTable.put( "for", Symbol.FOR );
       keywordsTable.put( "in", Symbol.IN );
       keywordsTable.put( "print", Symbol.PRINT );
       keywordsTable.put( "println", Symbol.PRINTLN );
    }
	
	public void nextToken() {
        char ch;

        while ((ch = input[tokenPos]) == ' ' || ch == '\r' ||
                 ch == '\t' || ch == '\n')  {
            // count the number of lines
          if (ch == '\n')
            lineNumber++;
          tokenPos++;
          }
        if ( ch == '\0')
          token = Symbol.EOF;
        else
          if ( input[tokenPos] == '/' && input[tokenPos + 1] == '/' ) {
                // comment found
               while ( input[tokenPos] != '\0'&& input[tokenPos] != '\n' )
                 tokenPos++;
               nextToken();
               }
          else {
            if ( Character.isLetter( ch ) ) {
                // get an identifier or keyword
                StringBuffer ident = new StringBuffer();
                while ( Character.isLetter( input[tokenPos] ) ||
                		Character.isDigit(input[tokenPos])) {
                    ident.append(input[tokenPos]);
                    tokenPos++;
                }
                
                stringValue = ident.toString();
                
                // if identStr is in the list of keywords, it is a keyword !
                Symbol value = keywordsTable.get(stringValue);
                if ( value == null )
                  token = Symbol.IDENT;
                else
                  token = value;
            }
            else if ( Character.isDigit( ch ) ) {
                // get a number
                StringBuffer number = new StringBuffer();
                while ( Character.isDigit( input[tokenPos] ) ) {
                    number.append(input[tokenPos]);
                    tokenPos++;
                }
                token = Symbol.NUMBER;
                try {
                   numberValue = Integer.valueOf(number.toString()).intValue();
                } catch ( NumberFormatException e ) {
                   error.signal("Number out of limits");
                }
                if ( numberValue >= MaxValueInteger )
                   error.signal("Number out of limits");

            } else {
                tokenPos++;
                switch ( ch ) {
                    case '+' :
                      token = Symbol.PLUS;
                      break;
                    case '-' :
                      token = Symbol.MINUS;
                      break;
                    case '*' :
                      token = Symbol.MULT;
                      break;
                    case '/' :
                      token = Symbol.DIV;
                      break;
                    case '%' :
                      token = Symbol.REMAINDER;
                      break;
                    case '<' :
                      if ( input[tokenPos] == '=' ) {
                        tokenPos++;
                        token = Symbol.LE;
                      }
                      else
                        token = Symbol.LT;
                      break;
                    case '>' :
                      if ( input[tokenPos] == '=' ) {
                        tokenPos++;
                        token = Symbol.GE;
                      }
                      else
                        token = Symbol.GT;
                      break;
                    case '=' :
                      if ( input[tokenPos] == '=' ) {
                        tokenPos++;
                        token = Symbol.EQ;
                      }
                      else
                        token = Symbol.ASSIGN;
                      break;
                    case '(' :
                      token = Symbol.LEFTPAR;
                      break;
                    case ')' :
                      token = Symbol.RIGHTPAR;
                      break;
                    case '{' :
                        token = Symbol.LEFTCURL;
                        break;
                    case '}' :
                        token = Symbol.RIGHTCURL;
                        break;
                    case ',' :
                      token = Symbol.COMMA;
                      break;
                    case ';' :
                      token = Symbol.SEMICOLON;
                      break;
                    case ':' :
                      token = Symbol.COLON;
                      break;
                    case '.' :
                    	if(input[tokenPos] != '.') {
                    		error.signal("Illegal literal character" + input[tokenPos-1] );
                    	}
                    	token = Symbol.DOUBLEDOTS;
                		tokenPos++;
                        break;
                    case '\'' :
                      token = Symbol.CHARACTER;
                      charValue = input[tokenPos];
                      tokenPos++;
                      if ( input[tokenPos] != '\'' )
                        error.signal("Illegal literal character" + input[tokenPos-1] );
                      tokenPos++;
                      break;
                    case '&' : 
                    	if(input[tokenPos] != '&') {
                    		error.signal("Illegal literal character" + input[tokenPos-1] );
                    	}
                    	token = Symbol.AND;
                		tokenPos++;
                		break;
                    case '|' : 
                    	if(input[tokenPos] != '|') {
                    		error.signal("Illegal literal character" + input[tokenPos-1] );
                    	}
                    	token = Symbol.OR;
                		tokenPos++;
                		break;
                    case '!' : 
                    	token = Symbol.NOT;
                		break;
                    default :
                      error.signal("Invalid Character: '" + ch + "'");
                }
            }
          }
        lastTokenPos = tokenPos - 1;
    }
	
	public int getLineNumber() {
        return lineNumber;
    }

    public String getCurrentLine() {
        int i = lastTokenPos;
        if ( i == 0 )
          i = 1;
        else
          if ( i >= input.length )
            i = input.length;

        StringBuffer line = new StringBuffer();
          // go to the beginning of the line
        while ( i >= 1 && input[i] != '\n' )
          i--;
        if ( input[i] == '\n' )
          i++;
          // go to the end of the line putting it in variable line
        while ( input[i] != '\0' && input[i] != '\n' && input[i] != '\r' ) {
            line.append( input[i] );
            i++;
        }
        return line.toString();
    }

    public String getStringValue() {
       return stringValue;
    }

    public int getNumberValue() {
       return numberValue;
    }

    public char getCharValue() {
       return charValue;
    }
	
	// current token
    public Symbol token;
    private String stringValue;
    private int numberValue;
    private char charValue;

    private int  tokenPos;
    //  input[lastTokenPos] is the last character of the last token
    private int lastTokenPos;
    // program given as input - source code
    private char []input;

    // number of current line. Starts with 1
    private int lineNumber;

    private CompilerError error;
    private static final int MaxValueInteger = 32768;
}
