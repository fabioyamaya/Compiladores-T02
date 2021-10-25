package ast;

import java.util.Map;

public class PrintStat extends Statement {

	public PrintStat(Expr expr) {
		super();
		this.expr = expr;
	}

	@Override
	public void genC(PW pw) {
		if (expr.getType() == Type.integerType) {
			pw.print("printf(\"%d\", ", true);
		} else {
			pw.print("printf(\"%s\", ", true);
		}
		expr.genC(pw);
		pw.println(");", false);
	}

	@Override
	public void run(Map<String, Object> memory) {
		System.out.print(expr.run(memory).toString());
	}

	private Expr expr;
}
