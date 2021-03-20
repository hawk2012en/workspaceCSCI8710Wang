/**
 * @(#) ReplaceClassNameAnalyzer.java
 */
package analysis.replace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import model.ProgramElement;
import util.ParseUtil;
import util.UtilAST;

/**
 * @since J2SE-1.8
 */
public class RenameClassNameAnalyzer {
	private ProgramElement curProgElem;
	private String newClassName;

	public RenameClassNameAnalyzer(ProgramElement curProgElem, String newClassName) {
		this.curProgElem = curProgElem;
		this.newClassName = newClassName;
		// Get all projects in the workspace.
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			try {
				analyzeJavaProject(project);
			} catch (MalformedTreeException | BadLocationException | CoreException e) {
				e.printStackTrace();
			}
		}
	}

	void analyzeJavaProject(IProject project)
			throws CoreException, JavaModelException, MalformedTreeException, BadLocationException {
		// Check if we have a Java project.
		if (!project.isOpen() || !project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			return;
		}
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment iPackage : packages) {
			if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				if (iPackage.getCompilationUnits().length < 1) {
					continue;
				}
				replaceClassName(iPackage);
			}
		}
	}

	void replaceClassName(IPackageFragment iPackage)
			throws JavaModelException, MalformedTreeException, BadLocationException {
		if (iPackage.getElementName().equals(curProgElem.getPkgName()) == false) {
			return;
		}
		for (ICompilationUnit iCunit : iPackage.getCompilationUnits()) {
			String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCunit.getElementName());
			if (nameICUnit.equals(this.curProgElem.getClassName()) == false) {
				continue;
			}
			UtilAST.rename(iCunit, this.newClassName, IJavaRefactorings.RENAME_COMPILATION_UNIT);
		}
	}
	
}