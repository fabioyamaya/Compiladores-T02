package main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import ast.AssignStat;
import ast.CompilerError;
import ast.CompositeExpr;
import ast.Expr;
import ast.ForStat;
import ast.IfStat;
import ast.NumberExpr;
import ast.PrintLnStat;
import ast.PrintStat;
import ast.Program;
import ast.Statement;
import ast.StatementList;
import ast.UnaryExpr;
import ast.Variable;
import ast.WhileStat;
import ast.Type;
import ast.VariableExpr;
import lexer.Lexer;
import lexer.Symbol;

public class Compiler {

	public Program compile(char[] input, PrintWriter outError) {
		symbolTable = new Hashtable<String, Variable>();
		error = new CompilerError(outError);
		lexer = new Lexer(input, error);
		error.setLexer(lexer);

		Program p = null;

		lexer.nextToken();
		p = program();

		if (lexer.token != Symbol.EOF)
			error.signal("EOF expected");

		return p;
	}

	private Program program() {
		ArrayList<Variable> arrayVariable = null;
		ArrayList<Statement> arrayStat = new ArrayList<Statement>();

		arrayVariable = varList();

		while (lexer.token != Symbol.EOF) {
			arrayStat.add(stat());
		}

		StatementList statList = new StatementList(arrayStat);
		return new Program(arrayVariable, statList);
	}

	private ArrayList<Variable> varList() {
		ArrayList<Variable> arrayVariable = new ArrayList<Variable>();

		while (lexer.token == Symbol.VAR) {
			lexer.nextToken();
			Type varType = type();
			String name = lexer.getStringValue();
			ident();

			if (lexer.token != Symbol.SEMICOLON) {
				error.signal("Missing semicolon");
			}

			if (symbolTable.get(name) != null)
				error.signal("Variable " + name + " has already been declared");

			Variable v = new Variable(name, varType);

			symbolTable.put(name, v);

			arrayVariable.add(v);

			lexer.nextToken();
		}
		return arrayVariable;
	}
	
	private Type type() {
        Type result;

        switch ( lexer.token ) {
            case INTEGER :
              result = Type.integerType;
              break;
            case BOOLEAN :
              result = Type.booleanType;
              break;
            case STRING :
              result = Type.stringType;
              break;
            default :
              error.signal("Type expected");
              result = null;
        }
        lexer.nextToken();
        return result;
    }

	private Statement stat() {
		switch (lexer.token) {
		case IDENT:
			return assignStat();
		case IF:
			return ifStat();
		case FOR:
			return forStat();
		case PRINT:
			return printStat();
		case PRINTLN:
			return printLnStat();
		case WHILE:
			return whileStat();
		default:
			error.signal("Statement expected");
		}
		return null;
	}

	private void ident() {
		if (lexer.token != Symbol.IDENT) {
			error.signal("Identifier of variable expected");
		}
		lexer.nextToken();
		return;
	}

	private AssignStat assignStat() {

		String name = lexer.getStringValue();

		Variable v = symbolTable.get(name);

		if (v == null)
			error.signal("Variable " + name + " was not declared");

		lexer.nextToken();
		if (lexer.token != Symbol.ASSIGN) {
			error.signal("= expected");
		}
		lexer.nextToken();

		Expr e = expr();

		AssignStat assignStat = new AssignStat(v, e);

		if (lexer.token != Symbol.SEMICOLON) {
			error.signal("; expected");
		}

		lexer.nextToken();

		return assignStat;
	}

	private IfStat ifStat() {
		StatementList thenPart = null;
		StatementList elsePart = null;

		lexer.nextToken();
		Expr e = expr();
		thenPart = statList();

		if (lexer.token == Symbol.ELSE) {
			lexer.nextToken();
			elsePart = statList();
		}

		return new IfStat(e, thenPart, elsePart);
	}

	private ForStat forStat() {
		lexer.nextToken();
		String name = lexer.getStringValue();
		ident();

		Variable v = symbolTable.get(name);

		if (v != null)
			error.signal("Variable " + name + " has already been declared");

		v = new Variable(name);

		symbolTable.put(name, v);

		if (lexer.token != Symbol.IN) {
			error.signal("in keyword expected");
		}

		lexer.nextToken();
		Expr startExpr = expr();

		if (lexer.token != Symbol.DOUBLEDOTS) {
			error.signal(".. keyword expected");
		}

		lexer.nextToken();
		Expr endExpr = expr();

		StatementList s = statList();

		symbolTable.remove(name);
		return new ForStat(v, startExpr, endExpr, s);
	}

	private PrintStat printStat() {
		lexer.nextToken();
		Expr e = expr();
		if (lexer.token != Symbol.SEMICOLON) {
			error.signal("; expected");
		}
		lexer.nextToken();
		return new PrintStat(e);
	}

	private PrintLnStat printLnStat() {
		lexer.nextToken();
		Expr e = expr();
		if (lexer.token != Symbol.SEMICOLON) {
			error.signal("; expected");
		}
		lexer.nextToken();

		return new PrintLnStat(e);
	}

	private StatementList statList() {
		ArrayList<Statement> arrayStat = new ArrayList<Statement>();

		if (lexer.token != Symbol.LEFTCURL) {
			error.signal("{ expected");
		}
		lexer.nextToken();
		while (lexer.token != Symbol.RIGHTCURL) {
			arrayStat.add(stat());
		}
		lexer.nextToken();

		return new StatementList(arrayStat);
	}

	private WhileStat whileStat() {
		lexer.nextToken();

		Expr e = expr();

		StatementList s = statList();

		return new WhileStat(s, e);
	}

	private Expr expr() {
		Expr left, right;
		left = andExpr();

		if (lexer.token == Symbol.OR) {
			lexer.nextToken();
			right = andExpr();

			left = new CompositeExpr(left, Symbol.OR, right);
		}

		return left;
	}

	private Expr andExpr() {
		Expr left, right;
		left = relExpr();
		if (lexer.token == Symbol.AND) {
			lexer.nextToken();
			right = relExpr();

			left = new CompositeExpr(left, Symbol.AND, right);
		}
		return left;
	}

	private Expr relExpr() {
		Expr left, right;
		left = addExpr();
		Symbol op = lexer.token;
		if (lexer.token == Symbol.LT || lexer.token == Symbol.LE || lexer.token == Symbol.GT || lexer.token == Symbol.GE
				|| lexer.token == Symbol.EQ || lexer.token == Symbol.NEQ) {
			lexer.nextToken();
			right = addExpr();

			left = new CompositeExpr(left, op, right);
		}

		return left;
	}

	private Expr addExpr() {
		Expr left, right;
		left = multExpr();

		Symbol op;
		while ( (op = lexer.token) == Symbol.PLUS ||
	              op == Symbol.MINUS ) {
			lexer.nextToken();
			right = multExpr();

			left = new CompositeExpr(left, op, right);
		}

		return left;
	}

	private Expr multExpr() {
		Expr left, right;

		left = simpleExpr();
		Symbol op;
		while ( (op = lexer.token) == Symbol.MULT ||
                op == Symbol.DIV || op == Symbol.REMAINDER ) {
			lexer.nextToken();
			right = simpleExpr();

			left = new CompositeExpr(left, op, right);

		}
		return left;
	}

	private Expr simpleExpr() {

		Expr e = null;

		switch (lexer.token) {
		case NUMBER:
			e = new NumberExpr(lexer.getNumberValue());
			lexer.nextToken();
			break;
		case LEFTPAR:
			lexer.nextToken();
			e = expr();
			if (lexer.token != Symbol.RIGHTPAR) {
				error.signal(") expected");
			}
			lexer.nextToken();
			break;
		case NOT:
			lexer.nextToken();
			e = expr();
			e = new UnaryExpr(e, Symbol.NOT);
			break;
		case PLUS:
			lexer.nextToken();
			e = expr();
			e = new UnaryExpr(e, Symbol.PLUS);
			break;
		case MINUS:
			lexer.nextToken();
			e = expr();
			e = new UnaryExpr(e, Symbol.MINUS);
			break;
		case IDENT:
			String name = lexer.getStringValue();
			ident();

			Variable v = symbolTable.get(name);

			if (v == null)
				error.signal("Variable " + name + " was not declared");

			e = new VariableExpr(v);
			break;
		default:
			error.signal("Expression expected");
		}
		return e;
	}

	private Hashtable<String, Variable> symbolTable;
	private Lexer lexer;
	private CompilerError error;

}
