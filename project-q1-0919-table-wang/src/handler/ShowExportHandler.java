 
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ModelProvider;
import model.ProgramElement;
import util.UtilFile;

public class ShowExportHandler {
	@Execute
	public void execute(Shell shell) {
//		String basePath = System.getProperty("user.dir");
//		System.out.println(basePath);
    	List<ProgramElement> progElements = ModelProvider.INSTANCE.getProgramElements();
        List<String> contents = new ArrayList<String>();
        String tableHead = "Package Name" + ", Class Name" + ", Method Name" + 
				", is Return Void" + ", is Public Modifier";
        contents.add(tableHead);
        for (ProgramElement progElement : progElements) {
            //System.out.println(progElement);
            contents.add(progElement.toString());
        }
        try {
			UtilFile.saveFile("D:\\output.csv", contents);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int linesExported = contents.size() - 1;
		
		MessageDialog.openInformation(shell, "Export", "Info: Exported " + linesExported + " lines.");
	}
		
}