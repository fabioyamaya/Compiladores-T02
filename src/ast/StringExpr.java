package ast;

import java.util.Map;

public class StringExpr extends Expr{
	public StringExpr(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public void genC(PW pw) {
		pw.print("\"" + value + "\"", false);
	}

	@Override
	public String run(Map<String, Object> memory) {
		return value;
	}

	@Override
	public Type getType() {
		return Type.stringType;
	}

	private String value;
}

