package ast;

public class BooleanType extends Type {

	public BooleanType() {
		super("boolean");
	}

	@Override
	public String getCname() {
		return "int";
	}
}
