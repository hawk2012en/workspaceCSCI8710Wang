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
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import model.ProgramElement;
import util.ParseUtil;
import visitor.rewrite.ReplacePackageVisitor;

/**
 * @since J2SE-1.8
 */
public class ReplacePackageNameAnalyzer {
	private ProgramElement curProgElem;
	private String newPackageName;

	public ReplacePackageNameAnalyzer(ProgramElement curProgElem, String newPackageName) {
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
				replacePackageName(iPackage);
			}
		}
	}

	void replacePackageName(IPackageFragment iPackage)
			throws JavaModelException, MalformedTreeException, BadLocationException {
		if (iPackage.getElementName().equals(curProgElem.getPkgName()) == false) {
			return;
		}
		for (ICompilationUnit iCunit : iPackage.getCompilationUnits()) {
			String nameICUnit = ParseUtil.getClassNameFromJavaFile(iCunit.getElementName());
			if (nameICUnit.equals(this.curProgElem.getClassName()) == false) {
				continue;
			}
			ICompilationUnit workingCopy = iCunit.getWorkingCopy(null);
			CompilationUnit cUnit = parse(workingCopy);
			ASTRewrite rewrite = ASTRewrite.create(cUnit.getAST());
			ReplacePackageVisitor visitor = new ReplacePackageVisitor(curProgElem, newPackageName);
			visitor.setICompilationUnit(iCunit);
			visitor.setRewrite(rewrite);
			visitor.setCompilationUnit(cUnit);

			cUnit.accept(visitor);
			TextEdit edits = null;
			try {
				// Compute the edits
				edits = rewrite.rewriteAST();
				// Apply the edits.
				workingCopy.applyTextEdit(edits, null);
				// Save the changes.
				workingCopy.commitWorkingCopy(false, null);
			} catch (Exception e) {
				// silence
			}
		}
	}

	static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS10);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
}