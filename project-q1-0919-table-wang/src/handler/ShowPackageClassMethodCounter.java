 
package handler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ModelProvider;
import model.ProgramElement;

public class ShowPackageClassMethodCounter {
	@Execute
	public void execute(Shell shell) {
		List<ProgramElement> progElements = ModelProvider.INSTANCE.getProgramElements();
		Set<String> packages = new HashSet<String>();
		Set<String> classes = new HashSet<String>();
		Set<String> methods = new HashSet<String>();
        for (ProgramElement progElement : progElements) {
            //System.out.println(progElement);
            packages.add(progElement.getPkgName());
            classes.add(progElement.getClassName());
            methods.add(progElement.getMethodName());
        }
		MessageDialog.openInformation(shell, "Package/Class/Method Counter", "Info: Found " + packages.size() + " packages, "
				+ classes.size() + " classes and " + methods.size() + " methods in total.");
	}
		
}