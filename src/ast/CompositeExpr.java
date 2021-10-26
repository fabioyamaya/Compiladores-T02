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

			if (left.getType() == Type.stringType) {
				if (right.getType() == Type.integerType) {
					pw.print("concatSI(", false);
					left.genC(pw);
					pw.print(", ", false);
					right.genC(pw);
					pw.print(", 1", false);
					pw.print(")", false);
				} else if (right.getType() == Type.booleanType) {
					pw.print("concatSB(", false);
					left.genC(pw);
					pw.print(", ", false);
					right.genC(pw);
					pw.print(", 1", false);
					pw.print(")", false);
				} else {
					pw.print("concatSS(", false);
					left.genC(pw);
					pw.print(", ", false);
					right.genC(pw);
					pw.print(", 1", false);
					pw.print(")", false);
				}
			} else if (left.getType() == Type.booleanType) {
				if (right.getType() == Type.integerType) {
					pw.print("concatBI(", false);
					left.genC(pw);
					pw.print(", ", false);
					right.genC(pw);
					pw.print(", 1", false);
					pw.print(")", false);
				} else if (right.getType() == Type.booleanType) {
					pw.print("concatBB(", false);
					left.genC(pw);
					pw.print(", ", false);
					right.genC(pw);
					pw.print(", 1", false);
					pw.print(")", false);
				} else {
					pw.print("concatSB(", false);
					right.genC(pw);
					pw.print(", ", false);
					left.genC(pw);
					pw.print(", 0", false);
					pw.print(")", false);
				}
			} else {
				if (right.getType() == Type.integerType) {
					pw.print("concatII(", false);
					left.genC(pw);
					pw.print(", ", false);
					right.genC(pw);
					pw.print(", 1", false);
					pw.print(")", false);
				} else if (right.getType() == Type.booleanType) {
					pw.print("concatBI(", false);
					right.genC(pw);
					pw.print(", ", false);
					left.genC(pw);
					pw.print(", 0", false);
					pw.print(")", false);
				} else {
					pw.print("concatSI(", false);
					right.genC(pw);
					pw.print(", ", false);
					left.genC(pw);
					pw.print(", 0", false);
					pw.print(")", false);
				}
			}

		} else {
			pw.print("(", false);
			left.genC(pw);
			pw.print(" " + oper.toString() + " ", false);
			right.genC(pw);
			pw.print(")", false);
		}
	}

	@Override
	public Object run(Map<String, Object> memory) {
		switch (oper) {
		// Operação aritmética
		case PLUS -> {
			Integer leftValue = (Integer) left.run(memory);
			Integer rightValue = (Integer) right.run(memory);
			return leftValue + rightValue;
		}
		case MINUS -> {
			Integer leftValue = (Integer) left.run(memory);
			Integer rightValue = (Integer) right.run(memory);
			return leftValue - rightValue;
		}
		case MULT -> {
			Integer leftValue = (Integer) left.run(memory);
			Integer rightValue = (Integer) right.run(memory);
			return leftValue * rightValue;
		}
		case DIV -> {
			Integer leftValue = (Integer) left.run(memory);
			Integer rightValue = (Integer) right.run(memory);
			return leftValue / rightValue;
		}
		case REMAINDER -> {
			Integer leftValue = (Integer) left.run(memory);
			Integer rightValue = (Integer) right.run(memory);
			return leftValue % rightValue;
		}
		// Operação booleana
		case OR -> {
			Boolean leftValue = (Boolean) left.run(memory);
			Boolean rightValue = (Boolean) right.run(memory);
			return leftValue || rightValue;
		}
		case AND -> {
			Boolean leftValue = (Boolean) left.run(memory);
			Boolean rightValue = (Boolean) right.run(memory);
			return leftValue && rightValue;
		}
		// Operação comum
		case EQ -> {
			return left.run(memory).equals(right.run(memory));
		}
		case GE -> {
			Object leftValue = left.run(memory);
			Object rightValue = right.run(memory);
			if (leftValue.equals(rightValue)) {
				return true;
			}
			if (left.getType() == Type.booleanType) {
				// false < true
				return (Boolean) leftValue;
			}
			if (left.getType() == Type.stringType) {
				String leftString = (String) leftValue;
				String rightString = (String) rightValue;
				return leftString.compareTo(rightString) > 0;
			}
			Integer leftInt = (Integer) leftValue;
			Integer rightInt = (Integer) rightValue;
			return leftInt > rightInt;
		}
		case GT -> {
			Object leftValue = left.run(memory);
			Object rightValue = right.run(memory);
			if (leftValue.equals(rightValue)) {
				return false;
			}
			if (left.getType() == Type.booleanType) {
				// false < true
				return (Boolean) leftValue;
			}
			if (left.getType() == Type.stringType) {
				String leftString = (String) leftValue;
				String rightString = (String) rightValue;
				return leftString.compareTo(rightString) > 0;
			}
			Integer leftInt = (Integer) leftValue;
			Integer rightInt = (Integer) rightValue;
			return leftInt > rightInt;
		}
		case LE -> {
			Object leftValue = left.run(memory);
			Object rightValue = right.run(memory);
			if (leftValue.equals(rightValue)) {
				return true;
			}
			if (left.getType() == Type.booleanType) {
				// false < true
				return (Boolean) rightValue;
			}
			if (left.getType() == Type.stringType) {
				String leftString = (String) leftValue;
				String rightString = (String) rightValue;
				return leftString.compareTo(rightString) < 0;
			}
			Integer leftInt = (Integer) leftValue;
			Integer rightInt = (Integer) rightValue;
			return leftInt < rightInt;
		}
		case LT -> {
			Object leftValue = left.run(memory);
			Object rightValue = right.run(memory);
			if (leftValue.equals(rightValue)) {
				return false;
			}
			if (left.getType() == Type.booleanType) {
				// false < true
				return (Boolean) rightValue;
			}
			if (left.getType() == Type.stringType) {
				String leftString = (String) leftValue;
				String rightString = (String) rightValue;
				return leftString.compareTo(rightString) < 0;
			}
			Integer leftInt = (Integer) leftValue;
			Integer rightInt = (Integer) rightValue;
			return leftInt < rightInt;
		}
		case NEQ -> {
			return !left.run(memory).equals(right.run(memory));
		}
		case CONCAT -> {
			Object leftValue = left.run(memory);
			Object rightValue = right.run(memory);
			return leftValue.toString() + rightValue.toString();
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
