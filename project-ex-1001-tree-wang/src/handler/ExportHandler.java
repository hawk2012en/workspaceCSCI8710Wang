 
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
	private int level;
	private List<String> contents = new ArrayList<String>();
	
	@Execute
	public void execute() {
		List<Person> persons = ModelProvider.INSTANCE.getPersons();		
		for(Person person : persons) {
			level = 0;			
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
		int linesExported = contents.size();
		
		MsgUtil.openInfo("Export", "Info: Exported " + linesExported + " lines.");
	}
		
	private void printPerson(Person parent) {
		String tab = "";
		for(int i = 0; i < level; i++) {
			tab += "\t";
		}
		//System.out.println(tab + parent + ":" + parent.getParent());
		if(parent.list().length != 0) {
			contents.add(tab + parent + "(" + parent.list().length +")");
		}
		else {
			contents.add(tab + parent);
		}
		if(parent.hasChildren()) {
			level++;
			for(Person child : parent.list()) {
				printPerson(child);
			}
			level--;
		}
	}
}