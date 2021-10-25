package ast;

import java.util.Map;

abstract public class Expr {
    abstract public void genC( PW pw );

    abstract public Object run(Map<String, Object> memory);
    abstract public Type getType();
}