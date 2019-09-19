 
package handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import util.UtilFile;

public class ShowHighestNumberHandler {
	@Execute
	public void execute(Shell shell) {
		String basePath = System.getProperty("user.dir");
		System.out.println(basePath);
		List<String> contents = UtilFile.readFile();
		Collections.sort(contents, Collections.reverseOrder()); 
		try {
			UtilFile.saveFile(contents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String output = "Top Five Numbers:";
		for (int i = 0; i < 5; i++) {
	         String line = contents.get(i);
	         if(i == 4)
	        	 output += " " + line + ".";
	         else
	        	 output += " " + line + ",";
		}
		
		MessageDialog.openInformation(shell, "Title", output);
	}
		
}