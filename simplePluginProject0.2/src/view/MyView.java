/*
 * @(#) MyView.java
 *
 */
package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.StyledText;

public class MyView extends ViewPart {

   public static final String ID = "view.MyView"; //$NON-NLS-1$
   private StyledText styledText;

   public MyView() {
   }

   @Override
   public void createPartControl(Composite parent) {
      Composite container = new Composite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
      container.setLayout(new FillLayout(SWT.HORIZONTAL));
      styledText = new StyledText(container, SWT.BORDER);
   }

   public void resetText() {
      this.styledText.setText("");
   }

   public void appendText(String s) {
      this.styledText.append(s);
   }

   @Override
   public void setFocus() {
   }
}
