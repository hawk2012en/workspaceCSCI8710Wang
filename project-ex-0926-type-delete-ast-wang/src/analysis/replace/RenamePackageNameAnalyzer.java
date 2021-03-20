/**
 * @(#) ReplaceClassNameAnalyzer.java
 */
package analysis.replace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import model.ProgramElement;
import util.UtilAST;

/**
 * @since J2SE-1.8
 */
public class RenamePackageNameAnalyzer {
	private ProgramElement curProgElem;
	private String newPackageName;

	public RenamePackageNameAnalyzer(ProgramElement curProgElem, String newPackageName) {
		this.curProgElem = curProgElem;
		this.newPackageName = newPackageName;
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
				renamePackageName(iPackage);
			}
		}
	}

	void renamePackageName(IPackageFragment iPackage)
			throws JavaModelException, MalformedTreeException, BadLocationException {
		if (iPackage.getElementName().equals(curProgElem.getPkgName()) == false) {
			return;
		}
		UtilAST.rename(iPackage, this.newPackageName, IJavaRefactorings.RENAME_PACKAGE);
	}

}