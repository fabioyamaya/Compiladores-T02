package ast;

import java.util.Map;

public class ForStat extends Statement {

	public ForStat(Variable v, Expr startExpr, Expr endExpr, StatementList statList) {
		super();
		this.v = v;
		this.startExpr = startExpr;
		this.endExpr = endExpr;
		this.statList = statList;
	}

	@Override
	public void genC(PW pw) {

		pw.print("for (int ", true);
		pw.print(v.getName(), false);
		pw.print(" = ", false);
		startExpr.genC(pw);
		pw.print("; ", false);
		pw.print(v.getName(), false);
		pw.print(" < ", false);
		endExpr.genC(pw);
		pw.print("; ++", false);
		pw.print(v.getName(), false);
		pw.println(") {", false);
		pw.add();
		statList.genC(pw);
		pw.sub();
		pw.println("}", true);
	}

	@Override
	public void run(Map<String, Object> memory) {
		for (int i = (Integer) startExpr.run(memory); i <= (Integer) endExpr.run(memory); i++) {
			memory.put(v.getName(), i);
			statList.run(memory);
		}
	}

	private Variable v;
	private Expr startExpr;
	private Expr endExpr;
	private StatementList statList;
}
