package lexer;

public enum Symbol {

    AND("&&"),
    ASSIGN("="),
    CHARACTER("character"),
    COLON(":"),
    COMMA(","),
    DIV("/"),
    DOUBLEDOTS(".."),
    ELSE("else"),
    EOF("eof"),
    EQ("=="),
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
    REMAINDER("%"),
    RIGHTCURL("}"),
    RIGHTPAR(")"),
    SEMICOLON(";"),
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
