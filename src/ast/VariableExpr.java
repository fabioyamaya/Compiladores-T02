package ast;

import java.util.Map;

public class VariableExpr extends Expr {

    public VariableExpr( Variable v ) {
        this.v = v;
    }

    @Override
	public void genC( PW pw ) {
        pw.print( v.getName(), false );
    }

    @Override
    public Integer run(Map<String, Integer> memory) {
        return memory.get(v.getName());
    }


    private Variable v;
}