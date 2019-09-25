package model;

import java.util.ArrayList;
import java.util.List;

import util.UtilFile;

public enum PersonModelProvider {
   INSTANCE;
   //INSTANCE(getFilePath()); // Call a constructor with a parameter. 

   private List<Person> persons;

   private PersonModelProvider() {
      persons = new ArrayList<Person>();
      persons.add(new Person("Rainer", "Zufall", "male", true));
      persons.add(new Person("Reiner", "Babbel", "male", true));
      persons.add(new Person("Marie", "Dortmund", "female", false));
      persons.add(new Person("Holger", "Adams", "male", true));
      persons.add(new Person("Juliane", "Adams", "female", true));
   }
   
// Load the data sets from a file dynamically. 
	private PersonModelProvider(String inputdata) {		
		List<String> contents = UtilFile.readFile(inputdata);
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);

		persons = new ArrayList<Person>();
		for (List<String> iList : tableContents) {                        			
			persons.add(new Person(iList.get(0), iList.get(1), iList.get(2), Boolean.parseBoolean(iList.get(3))));
		}
	}

	private static String getFilePath() {
		return "/Users/junwang/Documents/UNO/CSCI8710/workspaceCSCI8710/workspaceCSCI8710Wang/simpleTable3InsertDeleteExample/input.csv";
	}

   public List<Person> getPersons() {
      return persons;
   }
}
