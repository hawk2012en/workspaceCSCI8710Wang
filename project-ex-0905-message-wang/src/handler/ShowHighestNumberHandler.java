
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import util.UtilFile;

public class ShowHighestNumberHandler {
	@Execute
	public void execute(Shell shell) {
		String basePath = System.getProperty("user.dir");
		System.out.println("user.dir: " + basePath);
		String storageDir = System.getProperty("org.osgi.framework.storage");
		System.out.println("org.osgi.framework.storage: " + storageDir);
		
		String projectName = storageDir.substring(storageDir.lastIndexOf("\\") + 1);
		System.out.println("projectName: " + projectName);
		String workSpace = storageDir.substring(0, storageDir.indexOf("\\.meta"));
		System.out.println("workSpace: " + workSpace);
		String projectDir = workSpace + "\\" + projectName;
		System.out.println("projectDir: " + projectDir);
		
		String logfile = System.getProperty("osgi.logfile");
		System.out.println("osgi.logfile: " + logfile);	
		String runtimeWorkSpace = logfile.substring(0, logfile.indexOf("\\.meta"));
		System.out.println("runtimeWorkSpace: " + runtimeWorkSpace);

		String filePath = projectDir + "\\" + "numbers.csv";
		//String filePath = UtilFile.getInputPathString();
		if (filePath == null)
			return;
		List<String> contents = UtilFile.readFile(filePath);
		List<Integer> contents_integer = new ArrayList<Integer>();
		/*
		 * for (String str : contents) { contents_integer.add(Integer.parseInt(str)); }
		 */
		contents_integer = contents.stream().map(Integer::parseInt).collect(Collectors.toList());
		Collections.sort(contents_integer, Collections.reverseOrder());
		contents.clear();
		/*
		 * for (Integer number : contents_integer) { contents.add(number.toString()); }
		 */
		contents = contents_integer.stream().map(Object::toString).collect(Collectors.toList());
		filePath = projectDir + "\\" + "numbers_sorted_descending.csv";
		//filePath = UtilFile.getOutputPathString();
		try {
			UtilFile.saveFile(filePath, contents);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String output = "Top Five Numbers:";
		for (int i = 0; i < 5; i++) {
			String line = contents.get(i);
			if (i == 4)
				output += " " + line + ".";
			else
				output += " " + line + ",";
		}

		MessageDialog.openInformation(shell, "Title", output);
	}
}