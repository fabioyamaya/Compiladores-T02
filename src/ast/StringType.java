package ast;

public class StringType extends Type {

	public StringType() {
        super("char");
    }

	@Override
	public String getCname() {
		return "char";
	}

}
