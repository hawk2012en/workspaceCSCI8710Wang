 
package handler;

import java.util.List;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import model.ModelProvider;
import util.MsgUtil;
import util.UtilFile;
import view.Viewer;

public class ShowImportHandler {			
	@Execute
	public void execute(EPartService epartService) {
		String basePath = System.getProperty("user.dir");
		System.out.println(basePath);
		ModelProvider.INSTANCE.clearProgramElements();
		String filePath = UtilFile.getInputPathString();				
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
				
		MsgUtil.openInfo("Import", "Info: Imported " + lineCount + " lines.");
	}	
	
}