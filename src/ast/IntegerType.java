package ast;

public class IntegerType extends Type {

	public IntegerType() {
		super("integer");
	}

	@Override
	public String getCname() {
		return "int";
	}

}