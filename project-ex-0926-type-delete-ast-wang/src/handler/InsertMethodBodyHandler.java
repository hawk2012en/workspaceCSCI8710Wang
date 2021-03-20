/**
 * @(#) DelMethodHandler.java
 */
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Shell;

import analysis.replace.InsertMethodBodyAnalyzer;
import model.ProgramElement;
import view.AddProgElemDialog;
import view.MyTableViewer;

/**
 * @since J2SE-1.8
 */
public class InsertMethodBodyHandler {

	@Inject
	private ESelectionService selectionService;
	@Inject
	private EPartService epartService;
	Shell shell;

	@Execute
	public void execute() {
		System.out.println("InsertMethodBodyHandler!!");
		AddProgElemDialog dialog = new AddProgElemDialog(shell);
		dialog.open();
		if (dialog.getProgElem() != null) {
			ProgramElement element = dialog.getProgElem();
			String newStatements = element.getClassName();			

			MPart findPart = epartService.findPart(MyTableViewer.ID);
			Object findPartObj = findPart.getObject();
			if (findPartObj instanceof MyTableViewer) {

				if (selectionService.getSelection() instanceof ProgramElement) {
					ProgramElement p = (ProgramElement) selectionService.getSelection();
					new InsertMethodBodyAnalyzer(p, newStatements);					
					MyTableViewer v = (MyTableViewer) findPartObj;
					v.refresh();
				}
			}
		}
	}
}