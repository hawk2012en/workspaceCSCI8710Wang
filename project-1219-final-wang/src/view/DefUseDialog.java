/**
 * @(#) DefUseDialog.java
 */
package view;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @since J2SE-1.8
 */
public class DefUseDialog extends TitleAreaDialog {
	Text methodTxt, classTxt, varTxt;
	String variableName, methodName, className;

	public DefUseDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Specify where def-use should be applied \n  variable, class, and method names:");
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label lbVar = new Label(container, SWT.NONE);
		lbVar.setBounds(10, 10, 59, 20);
		lbVar.setText("Variable");

		Label lbMethod = new Label(container, SWT.NONE);
		lbMethod.setBounds(10, 35, 59, 20);
		lbMethod.setText("Method");

		Label lbClass = new Label(container, SWT.NONE);
		lbClass.setBounds(10, 60, 59, 20);
		lbClass.setText("Class");

		varTxt = new Text(container, SWT.BORDER);
		varTxt.setBounds(101, 10, 239, 24);
		varTxt.setText("x");

		methodTxt = new Text(container, SWT.BORDER);
		methodTxt.setBounds(101, 35, 239, 24);
		methodTxt.setText("foo");

		classTxt = new Text(container, SWT.BORDER);
		classTxt.setBounds(101, 60, 239, 24);
		classTxt.setText("ClassA");

		return parent;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText("OK");
		button.setFont(JFaceResources.getDialogFont());
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				variableName = varTxt.getText();
				className = classTxt.getText();
				methodName = methodTxt.getText();

				if (variableName.trim().isEmpty() || className.trim().isEmpty() || methodName.trim().isEmpty()) {
					return;
				}
				close();
			}
		});
	}

	public String getVariableName() {
		return this.variableName;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public String getClassName() {
		return this.className;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 330);
	}

}
