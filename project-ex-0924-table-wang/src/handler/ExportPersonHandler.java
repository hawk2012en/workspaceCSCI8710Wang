 
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import model.Person;
import model.PersonModelProvider;
import util.UtilFile;

public class ExportPersonHandler {
	@Execute
	public void execute(Shell shell) {
    	List<Person> persons = PersonModelProvider.INSTANCE.getPersons();
        List<String> contents = new ArrayList<String>();
        String tableHead = "First Name" + ", Last Name" + ", Phone Number" + ", Email";
        contents.add(tableHead);
        for (Person person : persons) {
            //System.out.println(person);
            contents.add(person.toString());
        }
        String filePath = getOutputPathString(shell);
        try {
			//UtilFile.saveFile("D:\\output.csv", contents);
			UtilFile.saveFile(filePath, contents);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int linesExported = contents.size() - 1;
		
		MessageDialog.openInformation(shell, "Export", "Info: Exported " + linesExported + " lines.");
	}
	
	private String getOutputPathString(Shell shell) {
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setOverwrite(true);
		fd.setText("Save file:");
		String filePath = "";
		filePath = fd.open();
		return filePath;
	}
		
}