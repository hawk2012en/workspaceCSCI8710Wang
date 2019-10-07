
package handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import util.UtilFile;

public class ShowHighestNumberHandler {		
	@Execute
	public void execute(Shell shell) {
		String basePath = System.getProperty("user.dir");
		System.out.println(basePath);

		String filePath = getInputPathString(shell);
		List<String> contents = UtilFile.readFile(filePath);
		List<Integer> contents_integer = new ArrayList<Integer>();		
/*		for (String str : contents) {
			contents_integer.add(Integer.parseInt(str));
		}*/
		contents_integer = contents.stream().map(Integer::parseInt).collect(Collectors.toList());
		Collections.sort(contents_integer, Collections.reverseOrder());
		contents.clear();
/*		for (Integer number : contents_integer) {
			contents.add(number.toString());
		}*/
		contents = contents_integer.stream().map(Object::toString).collect(Collectors.toList());
		filePath = getOutputPathString(shell);
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

	private String getInputPathString(Shell shell) {
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

	private String getOutputPathString(Shell shell) {
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setOverwrite(true);
		fd.setText("Save file:");
		String filePath = "";
		filePath = fd.open();
		return filePath;
	}

}