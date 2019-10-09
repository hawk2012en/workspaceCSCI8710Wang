 
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.e4.core.di.annotations.Execute;
import model.Person;
import model.PersonModelProvider;
import util.MsgUtil;
import util.UtilFile;

public class ExportPersonHandler {
	@Execute
	public void execute() {
    	List<Person> persons = PersonModelProvider.INSTANCE.getPersons();
        List<String> contents = new ArrayList<String>();
        String tableHead = "First Name" + ", Last Name" + ", Phone Number" + ", Email";
        contents.add(tableHead);
        for (Person person : persons) {
            //System.out.println(person);
            contents.add(person.toString());
        }
        String filePath = UtilFile.getOutputPathString();
        try {
			//UtilFile.saveFile("D:\\output.csv", contents);
			UtilFile.saveFile(filePath, contents);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int linesExported = contents.size() - 1;
		
		MsgUtil.openInfo("Export", "Info: Exported " + linesExported + " lines.");
	}	
		
}