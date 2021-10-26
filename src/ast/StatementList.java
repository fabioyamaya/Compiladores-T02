package ast;

import java.util.ArrayList;
import java.util.Map;

public class StatementList {

    public StatementList(ArrayList<Statement> v) {
        this.v = v;
    }

    public void genC( PW pw ) {

      for( Statement s : v ) {
          s.genC(pw);
      }
    }

    public void genCFreeStr(PW pw) {
    	for( Statement s : v ) {
    		if(s instanceof AssignStat) {
    			((AssignStat) s).genCFreeStr(pw);
    		}
        }
    }
    
    private ArrayList<Statement> v;

    public void run(Map<String, Object> memory) {
        for( Statement s : v ) {
            s.run(memory);
        }
    }
}
