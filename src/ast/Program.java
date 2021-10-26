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

		pw.println("// Funções ++\n"
				+ "int countDigitos(int n) {\r\n" + "    int count = 0;\r\n" + "    while (n != 0) {\r\n"
				+ "        n = n / 10;\r\n" + "        count++;\r\n" + "    }\r\n" + "    return count;\r\n" + "}\r\n"
				+ "\r\n" + "char* concatSI(char* string, int integer, int ordem) {\r\n"
				+ "    char* stringFinal = malloc(strlen(string) + countDigitos(integer) + 1);\r\n"
				+ "    if (ordem) {\r\n" + "        sprintf(stringFinal, \"%s%d\", string, integer);\r\n"
				+ "    } else {\r\n" + "        sprintf(stringFinal, \"%d%s\", integer, string);\r\n" + "    }\r\n"
				+ "    return stringFinal;\r\n" + "}\r\n" + "\r\n"
				+ "char* concatSB(char* string, int boolean, int ordem) {\r\n"
				+ "    char* stringFinal = malloc(strlen(string) + (boolean ? 4 : 5) + 1);\r\n" + "    if (ordem) {\r\n"
				+ "        sprintf(stringFinal, \"%s%s\", string, boolean ? \"true\" : \"false\");\r\n"
				+ "    } else {\r\n"
				+ "        sprintf(stringFinal, \"%s%s\", boolean ? \"true\" : \"false\", string);\r\n" + "    }\r\n"
				+ "    return stringFinal;\r\n" + "}\r\n" + "\r\n"
				+ "char* concatSS(char* string1, char* string2, int ordem) {\r\n"
				+ "    char* stringFinal = malloc(strlen(string1) + strlen(string2) + 1);\r\n" + "    if (ordem) {\r\n"
				+ "        sprintf(stringFinal, \"%s%s\", string1, string2);\r\n" + "    } else {\r\n"
				+ "        sprintf(stringFinal, \"%s%s\", string2, string1);\r\n" + "    }\r\n"
				+ "    return stringFinal;\r\n" + "}\r\n" + "\r\n"
				+ "char* concatBI(int boolean, int integer, int ordem) {\r\n"
				+ "    char* stringFinal = malloc((boolean ? 4 : 5) + countDigitos(integer) + 1);\r\n"
				+ "    if (ordem) {\r\n"
				+ "        sprintf(stringFinal, \"%s%d\", boolean ? \"true\" : \"false\", integer);\r\n"
				+ "    } else {\r\n"
				+ "        sprintf(stringFinal, \"%d%s\", integer, boolean ? \"true\" : \"false\");\r\n" + "    }\r\n"
				+ "    return stringFinal;\r\n" + "}\r\n" + "\r\n"
				+ "char* concatBB(int boolean1, int boolean2, int ordem) {\r\n"
				+ "    char* stringFinal = malloc((boolean1 ? 4 : 5) + (boolean2 ? 4 : 5) + 1);\r\n"
				+ "    if (ordem) {\r\n" + "        sprintf(stringFinal, \"%s%s\", boolean1 ? \"true\" : \"false\",\r\n"
				+ "                boolean2 ? \"true\" : \"false\");\r\n" + "    } else {\r\n"
				+ "        sprintf(stringFinal, \"%s%s\", boolean2 ? \"true\" : \"false\",\r\n"
				+ "                boolean1 ? \"true\" : \"false\");\r\n" + "    }\r\n" + "    return stringFinal;\r\n"
				+ "}\r\n" + "\r\n" + "char* concatII(int integer1, int integer2, int ordem) {\r\n"
				+ "    char* stringFinal =\r\n"
				+ "        malloc(countDigitos(integer1) + countDigitos(integer2) + 1);\r\n" + "    if (ordem) {\r\n"
				+ "        sprintf(stringFinal, \"%d%d\", integer1, integer2);\r\n" + "    } else {\r\n"
				+ "        sprintf(stringFinal, \"%d%d\", integer2, integer1);\r\n" + "    }\r\n"
				+ "    return stringFinal;\r\n" + "}", true);
		
		pw.println("", false);

		// Funcao de alocacao de memoria para strings
		pw.println("// Realiza alocação de memória para strings\r\n" + "void reallocStr(char **strPtr, int size){\r\n"
				+ "    char *tmp = NULL;\r\n" + "\r\n" + "    tmp = realloc(*strPtr, size + 1);\r\n" + "\r\n"
				+ "    if (!tmp) {\r\n" + "        free(*strPtr);\r\n"
				+ "        printf(\"Erro na alocacao de memoria\");\r\n" + "        exit(0);\r\n" + "    }\r\n"
				+ "    *strPtr = tmp;\r\n" + "}", true);
		pw.println("", false);

		// FunÃ§Ã£o printbool
		pw.println("// Imprime booleanos com valores true ou false\r\n" + "void printBool(int newline, int bool) {\n"
				+ "    if(bool) {\n" + "        printf(\"true\");  \n" + "    } else {\n"
				+ "        printf(\"false\");\n" + "    }\n" + "        \n" + "    if(newline) {\n"
				+ "        printf(\"\\n\");\n" + "    }\n" + "}", true);
		pw.println("", false);

		pw.println("void main() {", true);

		pw.add();

		pw.println("", true);
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
