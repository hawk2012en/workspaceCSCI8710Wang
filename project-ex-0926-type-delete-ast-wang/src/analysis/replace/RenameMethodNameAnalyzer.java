/**
 * @(#) ReplaceMethodNameAnalyzer.java
 */
package analysis.replace;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import model.ProgramElement;
import util.ParseUtil;
import util.UtilAST;

/**
 * @since J2SE-1.8
 */
public class RenameMethodNameAnalyzer {
	private ProgramElement curProgElem;
	private String newMethodName;
	private IMethod iMethod;

	public RenameMethodNameAnalyzer(ProgramElement curProgName, String newMethodName) {
		this.curProgElem = curProgName;
		this.newMethodName = newMethodName;

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
		if (!project.isOpen() || !project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			return;
		}
		IJavaProject javaProject = JavaCore.create(project);
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment iPackage : packages) {
			if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && //
					iPackage.getCompilationUnits().length >= 1 && //
					iPackage.getElementName().equals(curProgElem.getPkgName())) {
				replaceMethodName(iPackage);
			}
		}
	}

	void replaceMethodName(IPackageFragment iPackage)
			throws JavaModelException, MalformedTreeException, BadLocationException {
		for (ICompilationUnit iCUnit : iPackage.getCompilationUnits()) {
			String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCUnit.getElementName());
			if (nameICUnit.equals(this.curProgElem.getClassName()) == false) {
				continue;
			}
	         CompilationUnit cUnit = UtilAST.parse(iCUnit);

	         ASTVisitor iMethodFinder = new ASTVisitor() {
	            public boolean visit(MethodDeclaration node) {
	               if (node.getName().getFullyQualifiedName().equals(curProgElem.getMethodName())) {
	                  IJavaElement javaElement = node.resolveBinding().getJavaElement();
	                  if (javaElement instanceof IMethod) {
	                     iMethod = (IMethod) javaElement;
	                  }
	               }
	               return true;
	            }
	         };
	         cUnit.accept(iMethodFinder);
	         UtilAST.rename(iMethod, this.newMethodName, IJavaRefactorings.RENAME_METHOD);
		}
	}
}