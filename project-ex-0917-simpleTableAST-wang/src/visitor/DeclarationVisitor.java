/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package visitor;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.ModelProvider;

public class DeclarationVisitor extends ASTVisitor {

	private String pkgName;
	private String className;
	private String methodName;

	public DeclarationVisitor() {
	}

	@Override
	public boolean visit(CompilationUnit cUnit) {
		String workspaceDir = Platform.getLocation().toString();
		System.out.println(cUnit.getPackage().getName().getFullyQualifiedName());
		System.out.println(cUnit.getJavaElement().getElementName());
		System.out.println(workspaceDir + cUnit.getJavaElement().getPath().makeAbsolute());
		System.out.println(cUnit.getNodeType() == ASTNode.COMPILATION_UNIT);
		// return false;
		return super.visit(cUnit);
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
		System.out.println(className + ": " + typeDecl.modifiers());
		return super.visit(typeDecl);
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
//		className = methodDecl.resolveBinding().getDeclaringClass().getName();
//		pkgName = methodDecl.resolveBinding().getDeclaringClass().getPackage().getName();
		methodName = methodDecl.getName().getIdentifier();
		Type returnType = methodDecl.getReturnType2();
		boolean isRetVoid = false;
		if (returnType.isPrimitiveType()) {
			PrimitiveType pt = (PrimitiveType) returnType;
			if (pt.getPrimitiveTypeCode().equals(PrimitiveType.VOID)) {
				isRetVoid = true;
			}
		}
		int parmSize = methodDecl.parameters().size();
		int startPos = methodDecl.getStartPosition();
		ModelProvider.INSTANCE.addProgramElements(pkgName, className, methodName, isRetVoid, parmSize, startPos);
		return super.visit(methodDecl);
	}
}
