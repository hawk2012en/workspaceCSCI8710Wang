/**
 * @(#) CalleeNameDialog.java
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
public class CalleeNameDialog extends TitleAreaDialog {
	Text methodTxt, classTxt;
	String methodName, className;

	public CalleeNameDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		setTitle("Specify callee method name and class name:");
		return contents;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label lbMethod = new Label(container, SWT.NONE);
		lbMethod.setBounds(10, 10, 59, 20);
		lbMethod.setText("Method");

		Label lbClass = new Label(container, SWT.NONE);
		lbClass.setBounds(10, 35, 59, 20);
		lbClass.setText("Class");

		methodTxt = new Text(container, SWT.BORDER);
		methodTxt.setBounds(101, 10, 239, 24);
		methodTxt.setText("m1");

		classTxt = new Text(container, SWT.BORDER);
		classTxt.setBounds(101, 35, 239, 24);
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
				className = classTxt.getText();
				methodName = methodTxt.getText();

				if (className.trim().isEmpty() || methodName.trim().isEmpty()) {
					return;
				}
				close();
			}
		});
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
