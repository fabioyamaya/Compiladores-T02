package ast;

import java.util.Map;

public class NumberExpr extends Expr {

	public NumberExpr(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void genC(PW pw) {
		pw.print(String.valueOf(value), false);
	}

	@Override
	public Integer run(Map<String, Integer> memory) {
		return value;
	}

	private int value;
}
