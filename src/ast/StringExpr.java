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
	public Integer run(Map<String, Integer> memory) {
		return 1;
	}

	@Override
	public Type getType() {
		return Type.stringType;
	}

	private String value;
}

