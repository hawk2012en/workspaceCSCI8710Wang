/**
 * @(#) DelMethodHandler.java
 */
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import analysis.DelMethodAnalyzer;
import model.ModelProvider;
import model.ProgramElement;
import view.MyTableViewer;

/**
 * @since J2SE-1.8
 */
public class DelMethodHandler {

	@Inject
	private ESelectionService selectionService;
	@Inject
	private EPartService epartService;
	Shell shell;

	@Execute
	public void execute() {
		System.out.println("DelProgElemHandler!!");

		MPart findPart = epartService.findPart(MyTableViewer.ID);
		Object findPartObj = findPart.getObject();
		if (findPartObj instanceof MyTableViewer) {

			if (selectionService.getSelection() instanceof ProgramElement) {
				ProgramElement p = (ProgramElement) selectionService.getSelection();
				if (p.isModifierPublic()) {
					MessageDialog.openInformation(shell, "Warning", "Can not delte selected method " + p.getMethodName()
							+ " because it is not a private method!");
				} else {
					ModelProvider.INSTANCE.getProgramElements().remove(p);
					MyTableViewer v = (MyTableViewer) findPartObj;
					v.refresh();
					new DelMethodAnalyzer(p);
				}
			}
		}
	}
}