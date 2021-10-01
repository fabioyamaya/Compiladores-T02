package ast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Program {

	public Program(ArrayList<Variable> arrayVariable, StatementList statementList) {
		this.arrayVariable = arrayVariable;
		this.statementList = statementList;
	}

	public void genC(PW pw) {
		pw.println("#include <stdio.h>", true);
		pw.println("", false);
		pw.println("void main() {", true);

		pw.add();
		// generate code for the declaration of variables
		for (Variable v : arrayVariable) {
			pw.println("int" + " " + v.getName() + ";", true);
		}
		pw.println("",true);
		statementList.genC(pw);
		pw.sub();
		pw.println("}", true);
	}

	public void run(Map<String, Integer> memory) {
		statementList.run(memory);
	}

	private ArrayList<Variable> arrayVariable;
	private StatementList statementList;
}
