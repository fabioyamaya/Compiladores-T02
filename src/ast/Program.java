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

		//Funcao de alocacao de memoria para strings
		pw.println("// Realiza alocação de memória para strings\r\n"
				+ "void reallocStr(char **strPtr, int size){\r\n"
				+ "    char *tmp = NULL;\r\n"
				+ "\r\n"
				+ "    tmp = realloc(*strPtr, size + 1);\r\n"
				+ "\r\n"
				+ "    if (!tmp) {\r\n"
				+ "        free(*strPtr);\r\n"
				+ "        printf(\"Erro na alocacao de memoria\");\r\n"
				+ "        exit(0);\r\n"
				+ "    }\r\n"
				+ "    *strPtr = tmp;\r\n"
				+ "}", true);
		pw.println("", false);
		
		//FunÃ§Ã£o printbool
		pw.println("// Imprime booleanos com valores true ou false\r\n" +
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

		pw.println("",true);
		statementList.genC(pw);
		
		// Libera memoria alocada
		statementList.genCFreeStr(pw);
		
		pw.sub();
		pw.println("}", true);
	}

	public void run(Map<String, Object> memory) {
		statementList.run(memory);
	}

	private StatementList statementList;
}
