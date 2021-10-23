package lexer;

public enum Symbol {

    AND("&&"),
    ASSIGN("="),
    BOOLEAN("Boolean"),
    CHARACTER("character"),
    COLON(":"),
    COMMA(","),
    CONCAT("++"),
    DIV("/"),
    DOUBLEDOTS(".."),
    ELSE("else"),
    EOF("eof"),
    EQ("=="),
    FALSE("false"),
    FOR("for"),
    GE(">="),
    GT(">"),
    IDENT("Ident"),
    IF("if"),
    IN("in"),
    INTEGER("Int"),
    LE("<="),
    LEFTCURL("{"),
    LEFTPAR("("),
    LT("<"),
    MINUS("-"),
    MULT("*"),
    NEQ("!="),
    NOT("!"),
    NUMBER("Number"),
    OR("||"),
    PLUS("+"),
    PRINT("print"),
    PRINTLN("println"),
    QUOTATION("\""),
    REMAINDER("%"),
    RIGHTCURL("}"),
    RIGHTPAR(")"),
    SEMICOLON(";"),
    STRING("String"),
    TRUE("true"),
    VAR("var"),
    WHILE("while");

    Symbol(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
        return name;
    }

    private String name;

	
}
