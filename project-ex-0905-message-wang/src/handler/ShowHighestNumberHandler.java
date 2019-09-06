 
package handler;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import util.UtilFile;


public class ShowHighestNumberHandler {
	@Execute
	public void execute() {
		List<String> tableContent = UtilFle.readFile("/Users/junwang/Documents/UNO/CSCI8710/ClassExercises/numbers.csv");
	}
		
}