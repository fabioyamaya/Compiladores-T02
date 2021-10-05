package ast;

import java.io.PrintWriter;

public class PW {

	public void add() {
		currentIndent += step;
	}

	public void sub() {
		currentIndent -= step;
	}

	public void set(PrintWriter out) {
		this.out = out;
		currentIndent = 0;
	}

	public void set(int indent) {
		currentIndent = indent;
	}

	public void print(String s, boolean ident) {
		if (ident) {
			out.print(space.substring(0, currentIndent));
			System.out.print(space.substring(0, currentIndent));
		}
		out.print(s);
		System.out.print(s);
	}

	public void println(String s, boolean newLine) {
		if (newLine) {
			out.print(space.substring(0, currentIndent));
			System.out.print(space.substring(0, currentIndent));
		}
		out.println(s);
		System.out.println(s);
	}

	int currentIndent = 0;
	static public final int green = 0, java = 1;
	int mode = green;
	public int step = 3;
	public PrintWriter out;

	static final private String space = "                                                                                                        ";

}
