package ast;

import java.util.Map;

public class AssignStat extends Statement {

	public AssignStat(Variable v, Expr expr) {
		this.v = v;
		this.expr = expr;
	}

	@Override
	public void genC(PW pw) {
		if (v.getType() == Type.stringType) {
			pw.println("reallocStr(&" + v.getName() + ", " + (((StringExpr) expr).getValue().length() + 1) + ");",
					true);
			pw.print("strcpy(" + v.getName() + ", ", true);
			expr.genC(pw);
			pw.println(");", false);
		} else if (v.getType() == Type.booleanType) {
			pw.print(v.getName() + " = ", true);
			pw.print(((BooleanExpr) expr).getValue() ? "1" : "0", false);
			pw.println(";", false);
		} else {
			pw.print(v.getName() + " = ", true);
			expr.genC(pw);
			pw.println(";", false);
		}
	}

	@Override
	public void run(Map<String, Object> memory) {
		memory.put(v.getName(), expr.run(memory));
	}

	public void genCFreeStr(PW pw) {
		if (v.getType() == Type.stringType) {
			pw.println("if(" + v.getName() + ")", true);
			pw.add();
			pw.println("free(" + v.getName() + ");", true);
			pw.sub();
		}
	}

	private Variable v;
	private Expr expr;
}
