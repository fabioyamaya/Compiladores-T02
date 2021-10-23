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
			pw.println("", true);
			pw.println("tmp = realloc(" + v.getName() + ", " + (((StringExpr) expr).getValue().length() + 1) + ");",
					true);
			pw.println("if (!tmp){", true);
			pw.add();
			pw.println("free(" + v.getName() + ");", true);
			pw.println("printf(\"Erro na alocacao de memoria\");", true);
			pw.println("return -1;", true);
			pw.sub();
			pw.println("}", true);
			pw.println(v.getName() + " = tmp;", true);
			pw.print("strcpy(" + v.getName() + ", ", true);
			expr.genC(pw);
			pw.println(");", false);
		}
		if (v.getType() == Type.booleanType) {
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
	public void run(Map<String, Integer> memory) {
		memory.put(v.getName(), expr.run(memory));
	}

	private Variable v;
	private Expr expr;
}
