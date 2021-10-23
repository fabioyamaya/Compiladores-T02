package ast;

import lexer.Symbol;

import java.util.Map;
import java.util.Objects;

public class CompositeExpr extends Expr {

	public CompositeExpr(Expr left, Symbol oper, Expr right) {
		this.left = left;
		this.oper = oper;
		this.right = right;
	}

	@Override
	public void genC(PW pw) {
		if (oper == Symbol.CONCAT) {
			pw.print("(\"", false);
			left.genC(pw);
			right.genC(pw);
			pw.print("\")", false);
		} else {
			pw.print("(", false);
			left.genC(pw);
			pw.print(" " + oper.toString() + " ", false);
			right.genC(pw);
			pw.print(")", false);
		}
	}

	@Override
	public Integer run(Map<String, Integer> memory) {
		Integer leftValue = left.run(memory);
		Integer rightValue = right.run(memory);
		switch (oper) {
		case PLUS -> {
			return leftValue + rightValue;
		}
		case MINUS -> {
			return leftValue - rightValue;
		}
		case OR -> {
			return leftValue != 0 || rightValue != 0 ? 1 : 0;
		}
		case AND -> {
			return leftValue != 0 && rightValue != 0 ? 1 : 0;
		}
		case EQ -> {
			return leftValue == rightValue ? 1 : 0;
		}
		case GE -> {
			return leftValue >= rightValue ? 1 : 0;
		}
		case GT -> {
			return leftValue > rightValue ? 1 : 0;
		}
		case LE -> {
			return leftValue <= rightValue ? 1 : 0;
		}
		case LT -> {
			return leftValue < rightValue ? 1 : 0;
		}
		case REMAINDER -> {
			return leftValue % rightValue;
		}
		case MULT -> {
			return leftValue * rightValue;
		}
		case DIV -> {
			return leftValue / rightValue;
		}
		case NEQ -> {
			return leftValue != rightValue ? 1 : 0;
		}
		default -> {
			System.out.println("Operador " + oper.toString() + " não implementado");
			return 0;
		}
		}

	}

	@Override
	public Type getType() {

		// left and right must be the same type
		if (oper == Symbol.EQ || oper == Symbol.NEQ || oper == Symbol.LE || oper == Symbol.LT || oper == Symbol.GE
				|| oper == Symbol.GT || oper == Symbol.AND || oper == Symbol.OR)
			return Type.booleanType;
		else if (oper == Symbol.CONCAT) 
			return Type.stringType;
		else
			return Type.integerType;

	}

	private Expr left, right;
	private Symbol oper;

}
