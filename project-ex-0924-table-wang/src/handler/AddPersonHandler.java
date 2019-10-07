
package handler;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import model.Person;
import model.PersonModelProvider;
import util.UtilFile;
//import util.MsgUtil;
//import view.AddPersonDialog;
import view.MyTableViewer;

public class AddPersonHandler {
	@Inject
	private EPartService epartService;
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)	
	Shell shell;
	
	@Execute
	public void execute() {
		// MsgUtil.openWarning("Hint", "Class Exercise!!");
		PersonModelProvider personInstance = PersonModelProvider.INSTANCE;
		String filePath = getInputPathString(shell);
		List<String> contents = UtilFile.readFile(filePath);
		//List<String> contents = UtilFile.readFile("D:\\input_add.csv");
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);

		int lineCount = 0;
		for (List<String> iList : tableContents) {
			personInstance.getPersons().add(new Person(iList.get(0), iList.get(1), iList.get(2), iList.get(3)));
			lineCount++;
		}

		MPart findPart = epartService.findPart(MyTableViewer.ID);
		Object findPartObj = findPart.getObject();

		if (findPartObj instanceof MyTableViewer) {
			MyTableViewer v = (MyTableViewer) findPartObj;
			v.refresh();
		}
		
		MessageDialog.openInformation(shell, "Add", "Info: Added " + lineCount + " more records.");

	}
	/*
	 * @Execute public void execute() { // MsgUtil.openWarning("Hint",
	 * "Class Exercise!!"); PersonModelProvider personInstance =
	 * PersonModelProvider.INSTANCE; AddPersonDialog dialog = new
	 * AddPersonDialog(shell); dialog.open(); if (dialog.getPerson() != null) {
	 * personInstance.getPersons().add(dialog.getPerson()); MPart findPart =
	 * epartService.findPart(MyTableViewer.ID); Object findPartObj =
	 * findPart.getObject();
	 * 
	 * if (findPartObj instanceof MyTableViewer) { MyTableViewer v = (MyTableViewer)
	 * findPartObj; v.refresh(); } }
	 * 
	 * }
	 */
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
