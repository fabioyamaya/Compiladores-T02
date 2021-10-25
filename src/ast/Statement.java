package ast;

import java.util.Map;

abstract public class Statement {
    abstract public void genC( PW pw );

    public abstract void run(Map<String, Object> memory);
}

