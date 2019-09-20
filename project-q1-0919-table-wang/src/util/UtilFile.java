package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class UtilFile {

   public static List<List<String>> convertTableContents(List<String> contents) {
      List<List<String>> tableContents = new ArrayList<List<String>>();

      for (int i = 0; i < contents.size(); i++) {
         String line = contents.get(i);
         if (line == null || line.isEmpty()) {
            continue;
         }

         List<String> listElements = new ArrayList<String>();
         String[] splitedLine = line.split(":");

         for (int j = 0; j < splitedLine.length; j++) {
            String iElem = splitedLine[j].trim();
            listElements.add(iElem);
         }
         tableContents.add(listElements);
      }
      return tableContents;
   }

   public static List<String> readFile() {
      List<String> contents = new ArrayList<String>();
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
      int result = fileChooser.showOpenDialog(null);      
      File selectedFile = null;
      if (result == JFileChooser.APPROVE_OPTION) {
          selectedFile = fileChooser.getSelectedFile();
          System.out.println("Selected file: " + selectedFile.getAbsolutePath());
      }      
      Scanner scanner = null;
      try {
         scanner = new Scanner(selectedFile);
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

   public static void saveFile(List<String> contents) throws IOException {
	   JFileChooser fileChooser = new JFileChooser();
	   fileChooser.setDialogTitle("Specify a file to save");  
	   fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	   int userSelection = fileChooser.showSaveDialog(null);
	   File fileToSave = null;
	   if (userSelection == JFileChooser.APPROVE_OPTION) {
	       fileToSave = fileChooser.getSelectedFile();
	       System.out.println("Save as file: " + fileToSave.getAbsolutePath());
	   }
      FileWriter fileWriter = new FileWriter(fileToSave.getAbsolutePath());
      PrintWriter printWriter = new PrintWriter(fileWriter);
      for (String str : contents) {
         printWriter.print(str + System.lineSeparator());
      }
      printWriter.close();
   }
}