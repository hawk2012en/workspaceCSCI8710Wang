 
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;

import model.ModelProvider;
import model.Person;
import util.MsgUtil;
import util.UtilFile;

public class ExportHandler {
	private String tab;
	private List<String> contents = new ArrayList<String>();
	
	@Execute
	public void execute() {
		List<Person> persons = ModelProvider.INSTANCE.getPersons();		
		for(Person person : persons) {
			tab = "";
			printPerson(person);
		}
		
		String filePath = UtilFile.getOutputPathString();
        try {
			//UtilFile.saveFile("D:\\output.txt", contents);
			UtilFile.saveFile(filePath, contents);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int linesExported = contents.size() - 1;
		
		MsgUtil.openInfo("Export", "Info: Exported " + linesExported + " lines.");
	}
		
	private void printPerson(Person parent) {
		//System.out.println(tab + parent.getName());
		contents.add(tab + parent.getName());
		if(parent.hasChildren()) {
			tab += "\t";
			for(Person child : parent.list()) {
				printPerson(child);
			}
		}
	}
}