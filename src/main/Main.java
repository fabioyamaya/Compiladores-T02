package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import ast.PW;
import ast.Program;

public class Main {
	public static void main(String[] args) {
		File file;
		FileReader stream;
		int numChRead;
		Program program;
		Compiler compiler = new Compiler();

		if (args.length != 2) {
			System.out.println("Usage:\n   main.Program option input.he");
			System.out.println(
					"option is \"-run\" for interpreting the file and \"-gen\" to generate C code from the file");
			System.out.println("input.he is the file to be compiled/interpreted");
			return;
		}
		file = new File(args[1].replace("\"", ""));
		if (!file.exists() || !file.canRead()) {
			System.out.println("Either the file " + args[1] + " does not exist or it cannot be read");
			throw new RuntimeException();
		}
		try {
			stream = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("Something wrong: file does not exist anymore");
			throw new RuntimeException();
		}

		char[] input = new char[(int) file.length() + 1];

		try {
			numChRead = stream.read(input, 0, (int) file.length());
		} catch (IOException e) {
			System.out.println("Error reading file " + args[1]);
			throw new RuntimeException();
		}

		if (numChRead != file.length()) {
			System.out.println("Read error");
			throw new RuntimeException();
		}
		try {
			stream.close();
		} catch (IOException e) {
			System.out.println("Error in handling the file " + args[1]);
			throw new RuntimeException();
		}

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream("output.c");
		} catch (IOException e) {
			System.out.println("Output file could not be opened for writing");
			throw new RuntimeException();
		}
		PrintWriter printWriter = new PrintWriter(outputStream);
		program = null;
		// the generated code goes to a file and so are the errors
		try {
			program = compiler.compile(input, printWriter);
		} catch (RuntimeException e) {
			System.out.println(e);
		}
		
		
		if (args[0].equals("-run")) {
			Map<String, Integer> memory = new HashMap<>();
			
			if (program != null) {
				PW pw = new PW();
				pw.set(printWriter);
				program.run(memory);
				if (printWriter.checkError()) {
					System.out.println("There was an error in the output");
				}
			}

		} else if (args[0].equals("-gen")) {

			if (program != null) {
				PW pw = new PW();
				pw.set(printWriter);
				program.genC(pw);
				if (printWriter.checkError()) {
					System.out.println("There was an error in the output");
				}
			}

		} else {
			System.out.println("invalid option");
		}

	}
}
