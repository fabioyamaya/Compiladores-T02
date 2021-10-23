package ast;

import java.util.Map;

public class BooleanExpr extends Expr {

    public BooleanExpr( boolean value ) {
        this.value = value;
    }

	public boolean getValue() {
		return value;
	}

    
    @Override
	public void genC( PW pw ) {
    	pw.out.print( value ? "1" : "0" );
    }

    @Override
	public Type getType() {
        return Type.booleanType;
    }

	@Override
	public Integer run(Map<String, Integer> memory) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static BooleanExpr True  = new BooleanExpr(true);
    public static BooleanExpr False = new BooleanExpr(false);

    private boolean value;
}

