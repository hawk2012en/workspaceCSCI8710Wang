 
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;

import model.ModelProvider;
import model.ProgramElement;
import util.MsgUtil;
import util.UtilFile;

public class ExportProgElemHandler {
	@Execute
	public void execute() {
		System.out.println("ExportProgElemHandler!!");
    	List<ProgramElement> progElements = ModelProvider.INSTANCE.getProgramElements();
        List<String> contents = new ArrayList<String>();
        String tableHead = "Package Name" + ", Class Name" + ", Method Name" + 
				", isReturnVoid" + ", Parameter size";
        contents.add(tableHead);
        for (ProgramElement progElement : progElements) {
            //System.out.println(progElement);
            contents.add(progElement.toString());
        }	    		
		String filePath = UtilFile.getOutputPathString();
        try {
			//UtilFile.saveFile("D:\\output.csv", contents);
			UtilFile.saveFile(filePath, contents);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int linesExported = contents.size() - 1;
		
		MsgUtil.openInfo("Export", "Info: Exported " + linesExported + " lines.");
	}
		
}