package ast;

import java.util.Map;

public class PrintStat extends Statement {
	
	public PrintStat(Expr expr) {
		super();
		this.expr = expr;
	}

	
	@Override
	public void genC(PW pw) {
		pw.print("printf(\"%d\", ", true);
		expr.genC(pw);
		pw.println(");", false);
	}

	@Override
	public void run(Map<String, Integer> memory) {
		System.out.print(expr.run(memory));
	}

	private Expr expr;
}
