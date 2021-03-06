package ast;

import lexer.Symbol;

import java.util.Map;

public class UnaryExpr extends Expr {

	public UnaryExpr(Expr expr, Symbol op) {
		this.expr = expr;
		this.op = op;
	}

	@Override
	public void genC(PW pw) {
		switch (op) {
			//Operação aritmética
			case PLUS:
				pw.print("+", false);
				break;
			case MINUS:
				pw.print("-", false);
				break;
			//Operação booleana
			case NOT:
				pw.print("!", false);
				break;
		}
		if (expr.getClass() == UnaryExpr.class) {
			pw.print("(", false);
			expr.genC(pw);
			pw.print(")", false);
		} else {
			expr.genC(pw);
		}
	}

	@Override
	public Object run(Map<String, Object> memory) {
		switch (op) {
			case PLUS:
				return +(Integer)expr.run(memory);
			case MINUS:
				return -(Integer)expr.run(memory);

			case NOT:
				return !(Boolean) expr.run(memory);
			default:
				System.out.println("Operação " + op.toString() + " não implementada");
				return 0;
		}
	}

	@Override
	public Type getType() {
		return expr.getType();
	}

	private Expr expr;
	private Symbol op;
}
