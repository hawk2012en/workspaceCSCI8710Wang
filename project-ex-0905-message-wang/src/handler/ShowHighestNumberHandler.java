 
package handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import util.UtilFile;

public class ShowHighestNumberHandler {
	@Execute
	public void execute(Shell shell) {
		String basePath = System.getProperty("user.dir");
		System.out.println(basePath);
		UtilFile utilFile = new UtilFile();
		utilFile.setSize(400, 400);
		utilFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		utilFile.setVisible(true);

		String filePath = utilFile.getInputPathString();
		List<String> contents = UtilFile.readFile(filePath);
		Collections.sort(contents, Collections.reverseOrder());
		filePath = utilFile.getOutputPathString();
		try {
			UtilFile.saveFile(filePath, contents);
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