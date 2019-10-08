 
package handler;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import model.ModelProvider;
import util.UtilFile;
import view.Viewer;

public class ShowImportHandler {
	@Inject
	private EPartService epartService;
	
	@Execute
	public void execute(Shell shell) {
		String basePath = System.getProperty("user.dir");
		System.out.println(basePath);
		ModelProvider.INSTANCE.clearProgramElements();
		String filePath = getInputPathString(shell);				
		//List<String> contents = UtilFile.readFile("D:\\input.csv");
		List<String> contents = UtilFile.readFile(filePath);
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
		
		MPart findPart = epartService.findPart(Viewer.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof Viewer) {
			Viewer v = (Viewer) findPartObj;
			v.refresh();
		}
		
		MessageDialog.openInformation(shell, "Import", "Info: Imported " + lineCount + " lines.");
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
}