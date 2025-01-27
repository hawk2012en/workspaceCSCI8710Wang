/**
 * @(#) ModelProvider.java
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @since J2SE-1.8
 */
public enum ModelProvider {
	INSTANCE;
	private List<ProgramElement> progElements = new ArrayList<ProgramElement>();;

	public void addProgramElements(String pkgName, String className, String methodName, boolean isRetVoid,
			int parmSize, boolean isModifierPrivate) {
		progElements.add(new ProgramElement(pkgName, className, methodName, isRetVoid, parmSize, isModifierPrivate));
	}

	public void addProgramElements(ProgramElement programElement) {
		progElements.add(programElement);
	}

	public List<ProgramElement> getProgramElements() {
		return progElements;
	}

	public void clearProgramElements() {
		progElements.clear();
	}
}
