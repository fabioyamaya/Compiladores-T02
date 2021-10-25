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
		pw.println("#include <stdlib.h>", true);
		pw.println("#include <string.h>", true);
		pw.println("", false);

		//Função printbool
		pw.println(
				"void printBool(int newline, int bool) {\n" +
				"    if(bool) {\n" +
				"        printf(\"true\");  \n" +
				"    } else {\n" +
				"        printf(\"false\");\n" +
				"    }\n" +
				"        \n" +
				"    if(newline) {\n" +
				"        printf(\"\\n\")\n" +
				"    }\n" +
				"}", true);
		pw.println("", false);

		pw.println("void main() {", true);
		pw.println("", false);
		
		pw.add();
		
		pw.println("// Variavel usada para gerenciar falha de realloc",true);
		pw.println("char *tmp = NULL",true);

		pw.println("",true);
		statementList.genC(pw);
		pw.sub();
		pw.println("}", true);
	}

	public void run(Map<String, Object> memory) {
		statementList.run(memory);
	}

	private StatementList statementList;
}
