package simpleVisitorPattern.visitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;

public class MyFileSaveVisitor extends CartPartVisitor {

	private String filePath = System.getProperty("user.dir");
	FileWriter fileWriter;
	PrintWriter printWriter;

	public MyFileSaveVisitor() {
		filePath = filePath + File.separator + "outputdata.csv";
	}

	@Override
	public void visit(Wheel part) {
		String line = part.getName() + "," + part.getModelNumberWheel() + "," + part.getModelYearWheel();
		// System.out.println(line);
		saveFile(line);
	}

	@Override
	public void visit(Engine part) {
		String line = part.getName() + "," + part.getModelNumberEngine() + "," + part.getModelYearEngine();
		/// System.out.println(line);
		saveFile(line);
	}

	@Override
	public void visit(Body part) {
		String line = part.getName() + "," + part.getModelNumberBody() + "," + part.getModelYearBody();
		// System.out.println(line);
		saveFile(line);
	}

	@Override
	public void visit(Break part) {
		String line = part.getName() + "," + part.getModelNumberBreak() + "," + part.getModelYearBreak();
		// System.out.println(line);
		saveFile(line);
	}

	public void saveFile(String line) {
		try {
			fileWriter = new FileWriter(filePath, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printWriter = new PrintWriter(fileWriter);
		printWriter.print(line + System.lineSeparator());
		printWriter.close();
	}
}
