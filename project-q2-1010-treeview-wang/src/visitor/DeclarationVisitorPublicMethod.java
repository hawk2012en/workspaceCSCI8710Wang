/**
 * @(#) DeclarationVisitor.java
 */
package visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.MethodElement;
import model.ProgElementModelProvider;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class DeclarationVisitorPublicMethod extends ASTVisitor {

	private String pkgName;
	private String className;
	private String methodName;
	private ProgramElement pkgElem;
	private ProgramElement classElem;
	private MethodElement methodElem;

	public DeclarationVisitorPublicMethod() {
	}

	@Override
	public boolean visit(PackageDeclaration pkgDecl) {
		this.pkgName = pkgDecl.getName().getFullyQualifiedName();
		this.pkgElem = new ProgramElement(pkgName);
		ProgramElement prePkg = ProgElementModelProvider.INSTANCE.addProgramElement(this.pkgElem);
		if (prePkg != null) {
			this.pkgElem = prePkg;
		}
		return super.visit(pkgDecl);
	}

	@Override
	public boolean visit(TypeDeclaration typeDecl) {
		this.className = typeDecl.getName().getIdentifier();
		this.classElem = new ProgramElement(className, this.pkgElem);
		this.pkgElem.add(classElem);
		ProgElementModelProvider.INSTANCE.classCount++;
		return super.visit(typeDecl);
	}

	public void endVisit(TypeDeclaration typeDecl) {
		if (this.classElem != null && !this.classElem.hasChildren()) {
			this.pkgElem.remove(this.classElem);
		}
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
		this.methodName = methodDecl.getName().getIdentifier();
		String className = methodDecl.resolveBinding().getDeclaringClass().getName();
		String pkgName = methodDecl.resolveBinding().getDeclaringClass().getPackage().getName();

		int methodModifers = methodDecl.getModifiers();
		boolean isPublic = (methodModifers & Modifier.PUBLIC) != 0;

		this.methodElem = new MethodElement(methodName, this.classElem);
		this.methodElem.setParameters(methodDecl.parameters());
		this.methodElem.setClassName(className);
		this.methodElem.setPkgName(pkgName);
		this.methodElem.setStartPos(methodDecl.getStartPosition());
		if (isPublic) {
			this.classElem.add(methodElem);
			ProgElementModelProvider.INSTANCE.methodCount++;
		}

		return super.visit(methodDecl);
	}
}
