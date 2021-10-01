package ast;

import java.util.Map;

public class PrintLnStat extends Statement {
	
	public PrintLnStat(Expr expr) {
		super();
		this.expr = expr;
	}
	
	@Override
	public void genC(PW pw) {
		pw.print("printf(\"%d\\n\", ", true);
		expr.genC(pw);
		pw.println(");", false);
	}

	@Override
	public void run(Map<String, Integer> memory) {
		System.out.println(expr.run(memory));
	}

	private Expr expr;
}

