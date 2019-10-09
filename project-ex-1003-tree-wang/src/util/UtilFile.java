package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class UtilFile{
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	static Shell shell = new Shell();

   public static List<List<String>> convertTableContents(List<String> contents) {
      List<List<String>> tableContents = new ArrayList<List<String>>();

      for (int i = 0; i < contents.size(); i++) {
         String line = contents.get(i);
         if (line == null || line.isEmpty()) {
            continue;
         }

         List<String> listElements = new ArrayList<String>();
         String[] splitedLine = line.split(",");

         for (int j = 0; j < splitedLine.length; j++) {
            String iElem = splitedLine[j].trim();
            listElements.add(iElem);
         }
         tableContents.add(listElements);
      }
      return tableContents;
   }
   
	public static List<String> readFile(String filePath) {
		List<String> contents = new ArrayList<String>();
		File file = new File(filePath);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				contents.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
		return contents;
	}

	public static void saveFile(String filePath, List<String> contents) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		for (String str : contents) {
			printWriter.print(str + System.lineSeparator());
		}
		printWriter.close();
	}

	public static String getInputPathString() {
		FileDialog fd = new FileDialog(shell, SWT.OPEN);
		fd.setText("Open .csv files");
		String[] filterExt = { "*.csv" };
		String[] filterNames = { "csv files" };
		fd.setFilterExtensions(filterExt);
		fd.setFilterNames(filterNames);
		String filePath = "";
		filePath = fd.open();
		return filePath;
	}

	public static String getOutputPathString() {
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setOverwrite(true);
		fd.setText("Save file:");
		String filePath = "";
		filePath = fd.open();
		return filePath;
	}
}