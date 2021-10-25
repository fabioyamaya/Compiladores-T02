package ast;

import java.util.ArrayList;
import java.util.Map;

public class VarListStat extends Statement {

	public VarListStat(ArrayList<Variable> arrayVariable) {
		super();
		this.arrayVariable = arrayVariable;
	}

	@Override
	public void genC(PW pw) {
		String t;
		for (Variable v : arrayVariable) {
			t = v.getType().getCname();
			if (t.equals("char")) {
				pw.println(t + " *" + v.getName() + " " + "= NULL" +";", true);
			} else {
				pw.println(t + " " + v.getName() + ";", true);
			}
		}
	}

	@Override
	public void run(Map<String, Object> memory) {
		// TODO Auto-generated method stub
	}

	private ArrayList<Variable> arrayVariable;
}
