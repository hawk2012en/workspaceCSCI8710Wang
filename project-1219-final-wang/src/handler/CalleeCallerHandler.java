/**
 * @file HandlerSearchMethodCaller.java
 */
package handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.swt.widgets.Shell;

import analysis.ProjectAnalyzerSearchMethodCallers;
import view.CalleeNameDialog;
import view.SimpleViewer;

/**
 * @since JavaSE-1.8
 */
public class CalleeCallerHandler {
	final String viewId = "project-1121-q3-wang.partdescriptor.simpleviewdef-usecallee-caller";

	@Inject
	private EPartService service;
	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	@Execute
	public void execute() {
		CalleeNameDialog dialog = new CalleeNameDialog(shell);
		dialog.open();
//		System.out.println("Callee Method Name: " + dialog.getMethodName());

		MPart part = service.findPart(viewId);
		if (part != null && part.getObject() instanceof SimpleViewer) {
			ProjectAnalyzerSearchMethodCallers analyzer = new ProjectAnalyzerSearchMethodCallers(
					dialog.getMethodName(), dialog.getClassName());
			try {
				analyzer.analyze();
			} catch (CoreException e) {
				e.printStackTrace();
			}
			List<Map<IMethod, IMethod[]>> calleeCallers = analyzer.getListCallers();
			SimpleViewer viewer = (SimpleViewer) part.getObject();
			display(viewer, calleeCallers);
		}
	}

	public void display(SimpleViewer viewer, List<Map<IMethod, IMethod[]>> calleeCallers) {
		for (Map<IMethod, IMethod[]> iCalleeCaller : calleeCallers) {
			for (Entry<IMethod, IMethod[]> entry : iCalleeCaller.entrySet()) {
				IMethod callee = entry.getKey();
				IMethod[] callers = entry.getValue();
				display(viewer, callee, callers);
			}
		}
	}

	private void display(SimpleViewer viewer, IMethod callee, IMethod[] callers) {
		viewer.reset();
		for (IMethod iCaller : callers) {
			String type = callee.getDeclaringType().getFullyQualifiedName();
			String calleeName = type + "." + callee.getElementName();
			viewer.appendText("'" + calleeName + //
					"' CALLED FROM '" + iCaller.getElementName() + "'\n");
			System.out.print("'" + calleeName + //
					"' CALLED FROM '" + iCaller.getElementName() + "'\n");
		}
	}
}