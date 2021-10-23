package ast;

import java.util.Map;

abstract public class Expr {
    abstract public void genC( PW pw );

    abstract public Integer run(Map<String, Integer> memory);
    abstract public Type getType();
}