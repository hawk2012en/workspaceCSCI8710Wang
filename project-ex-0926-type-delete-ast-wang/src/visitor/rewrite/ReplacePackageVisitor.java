/**
 * @(#) ReplaceClassVisitor.java
 */
package visitor.rewrite;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class ReplacePackageVisitor extends ASTVisitor {
	private ProgramElement curProgElem;
	private String newPackageName;
	private ICompilationUnit iUnit;
	private ASTRewrite rewrite;
	private CompilationUnit cUnit;

	public ReplacePackageVisitor(ProgramElement curProgElem, String newPackageName) {
		this.curProgElem = curProgElem;
		this.newPackageName = newPackageName;
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		if (node.getName().getFullyQualifiedName().equals(curProgElem.getPkgName()) == false) {
			return true;
		}
		// Description of the change
		Name oldName = node.getName();
		Name newName = cUnit.getAST().newName(newPackageName);
		

		try {
			// Update package java element accordingly
			IPackageFragment oldPackage = (IPackageFragment) iUnit.getParent();
			oldPackage.rename(newPackageName, true, null);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		rewrite.replace(oldName, newName, null);
		return super.visit(node);
	}

	public void setICompilationUnit(ICompilationUnit iUnit) {
		this.iUnit = iUnit;
	}

	public void setRewrite(ASTRewrite rewrite) {
		this.rewrite = rewrite;
	}

	public void setCompilationUnit(CompilationUnit cUnit) {
		this.cUnit = cUnit;
	}
}
