package ast;

import java.util.Map;

public class AssignStat extends Statement {

    public AssignStat( Variable v, Expr expr ) {
        this.v = v;
        this.expr = expr;
    }

    @Override
	public void genC( PW pw ) {
        pw.print(v.getName() + " = " , true);
        expr.genC(pw);
        pw.println(";", false);
    }

    @Override
    public void run(Map<String, Integer> memory) {
        memory.put(v.getName(), expr.run(memory));
    }

    private Variable v;
    private Expr expr;
}
