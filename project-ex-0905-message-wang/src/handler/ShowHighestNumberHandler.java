
package handler;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
		Collections.sort(contents, Collections.reverseOrder());
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

	public String getInputPathString(Shell shell) {
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

	public String getOutputPathString(Shell shell) {
		FileDialog fd = new FileDialog(shell, SWT.SAVE);
		fd.setOverwrite(true);
		fd.setText("Save file:");
		String filePath = "";
		filePath = fd.open();
		return filePath;
	}

}