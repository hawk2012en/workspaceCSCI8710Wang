/**
 * @(#) DeclarationVisitor.java
 */
package visitor;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import model.ModelProvider;
import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class DeclarationVisitor extends ASTVisitor {
	private String pkgName;
	private String className;
	private String methodName;

	public DeclarationVisitor() {
	}

	@Override
	public boolean visit(PackageDeclaration pkgDecl) {
		pkgName = pkgDecl.getName().getFullyQualifiedName();
		return super.visit(pkgDecl);
	}

	/**
	 * A type declaration is the union of a class declaration and an interface
	 * declaration.
	 */
	@Override
	public boolean visit(TypeDeclaration typeDecl) {
		className = typeDecl.getName().getIdentifier();
		return super.visit(typeDecl);
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
		methodName = methodDecl.getName().getIdentifier();
		int parmSize = methodDecl.parameters().size();
		Type returnType = methodDecl.getReturnType2();
		boolean isRetVoid = false;
		if (returnType.isPrimitiveType()) {
			PrimitiveType pt = (PrimitiveType) returnType;
			if (pt.getPrimitiveTypeCode().equals(PrimitiveType.VOID)) {
				isRetVoid = true;
			}
		}		
		int methodModifers = methodDecl.getModifiers();		
		boolean isPrivate = (methodModifers & Modifier.PRIVATE) != 0;
		//System.out.println(methodDecl.getName() + ": " + isPrivate);
		ProgramElement programElement = new ProgramElement(pkgName, className, methodName, isRetVoid, parmSize, isPrivate);
		ModelProvider.INSTANCE.addProgramElements(programElement);						
		return super.visit(methodDecl);
	}

	@Override
	public boolean visit(MethodInvocation methodInvocation) {
		String mi = methodInvocation.toString();
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", MethodInvocation: " + mi);
		return super.visit(methodInvocation);
	}

	@Override
	public boolean visit(SingleVariableDeclaration svd) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", SingleVariableDeclaration: " + svd);
		return super.visit(svd);
	}

	@Override
	public boolean visit(VariableDeclarationStatement vds) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", VariableDeclarationStatement: " + vds);
		for(Object object: vds.fragments()) {
			VariableDeclarationFragment vdf = (VariableDeclarationFragment) object;
			System.out.println(vdf.getName());
		}		
		return super.visit(vds);
	}

	@Override
	public boolean visit(VariableDeclarationFragment vdf) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", VariableDeclarationFragment: " + vdf);
		return super.visit(vdf);
	}

	@Override
	public boolean visit(Assignment assignment) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", Assignment: " + assignment);
		return super.visit(assignment);
	}

	@Override
	public boolean visit(ReturnStatement rs) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", ReturnStatement: " + rs);
		return super.visit(rs);
	}

	@Override
	public boolean visit(ForStatement fs) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", ForStatement: " + fs);
		return super.visit(fs);
	}

	@Override
	public boolean visit(IfStatement is) {
		System.out.println("Package: " + pkgName + ", Class: " + className + ", Method: " + methodName + ", IfStatement: " + is);
		return super.visit(is);
	}	
	
}
