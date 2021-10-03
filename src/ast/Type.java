package ast;

abstract public class Type {

    public Type( String name ) {
        this.name = name;
    }

    public static Type booleanType = new BooleanType();
    public static Type integerType = new IntegerType();
    public static Type stringType    = new StringType();

    public String getName() {
        return name;
    }

    abstract public String getCname();

    private String name;
}
