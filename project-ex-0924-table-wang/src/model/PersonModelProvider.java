package model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import util.UtilFile;

public enum PersonModelProvider {
//   INSTANCE;
//   INSTANCE(getFilePath()); // Call a constructor with a parameter. 
	INSTANCE(getInputPathString()); // Call a constructor with a parameter.

   private List<Person> persons;

   private PersonModelProvider() {
      persons = new ArrayList<Person>();
      persons.add(new Person("Emma", "Smith", "402-111-1111", "emmasmith@email.com"));
      persons.add(new Person("Olivia", "Johnson", "402-111-2222", "oliviajohnson@email.com"));
      persons.add(new Person("Liam", "Williams", "402-111-3333", "liamwilliams@email.com"));       
   }
   
// Load the data sets from a file dynamically. 
	private PersonModelProvider(String inputdata) {		
		List<String> contents = UtilFile.readFile(inputdata);
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);

		persons = new ArrayList<Person>();
		for (List<String> iList : tableContents) {                        			
			persons.add(new Person(iList.get(0), iList.get(1), iList.get(2), iList.get(3)));
		}
	}

	private static String getFilePath() {		
		return "D:\\input_init.csv";
	}

   public List<Person> getPersons() {
      return persons;
   }
   
	private static String getInputPathString() {		
	    final Shell shell = new Shell();
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
}
