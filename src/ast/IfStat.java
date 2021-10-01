package ast;

import java.util.Map;

public class IfStat extends Statement {

	public IfStat(Expr expr, StatementList thenPart, StatementList elsePart) {
		this.expr = expr;
		this.thenPart = thenPart;
		this.elsePart = elsePart;
	}

	@Override
	public void genC(PW pw) {

		pw.print("if ( ", true);
		expr.genC(pw);
		pw.println(" ) { ", false);
		pw.add();
		thenPart.genC(pw);
		pw.sub();
		pw.println("}", true);
		if (elsePart != null) {
			pw.println("else {", true);
			pw.add();
			elsePart.genC(pw);
			pw.sub();
			pw.println("}", true);
		}
	}

	@Override
	public void run(Map<String, Integer> memory) {
		if (expr.run(memory) != 0) {
			thenPart.run(memory);
		} else if (elsePart != null) {
			elsePart.run(memory);
		}
	}

	private Expr expr;
	private StatementList thenPart, elsePart;
}