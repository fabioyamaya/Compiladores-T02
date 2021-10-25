package ast;

import java.util.Map;

public class VariableExpr extends Expr {

	public VariableExpr(Variable v) {
		this.v = v;
	}

	@Override
	public void genC(PW pw) {
		pw.print(v.getName(), false);
	}

	@Override
	public Object run(Map<String, Object> memory) {
		return memory.get(v.getName());
	}

	@Override
	public Type getType() {
		return v.getType();
	}

	private Variable v;
}