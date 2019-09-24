 
package handler;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ModelProvider;
import util.UtilFile;

public class ShowImportHandler {
	@Execute
	public void execute(Shell shell) {
		String basePath = System.getProperty("user.dir");
		System.out.println(basePath);
		ModelProvider.INSTANCE.clearProgramElements();
		List<String> contents = UtilFile.readFile("D:\\input.csv");
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);
		int lineCount = 0;
		for (List<String> iList : tableContents) {
			String pkgName = iList.get(0);
			String className = iList.get(1);
			String methodName = iList.get(2);
			String isVoid = iList.get(3);
			String isPublic = iList.get(4);
			boolean isRetVoid = Boolean.parseBoolean(isVoid);
			boolean isModifierPublic = Boolean.parseBoolean(isPublic);
			ModelProvider.INSTANCE.addProgramElements(pkgName, className, methodName, isRetVoid, isModifierPublic);
			lineCount++;
		}	
		MessageDialog.openInformation(shell, "Import", "Info: Imported " + lineCount + " lines.");
	}		
}