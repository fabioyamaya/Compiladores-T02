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

	public Program(StatementList statementList) {
		this.statementList = statementList;
	}

	public void genC(PW pw) {
		pw.println("#include <stdio.h>", true);
		pw.println("", false);
		pw.println("void main() {", true);

		pw.add();

		pw.println("",true);
		statementList.genC(pw);
		pw.sub();
		pw.println("}", true);
	}

	public void run(Map<String, Integer> memory) {
		statementList.run(memory);
	}

	private StatementList statementList;
}
