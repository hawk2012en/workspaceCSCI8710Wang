/*
 * @(#) MethodVisitor.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */
package visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import model.progelement.MethodElement;
import model.progelement.ProgramElement;
import model.progelement.TypeElement;
import model.provider.ModelProviderProgElem;
import org.eclipse.jdt.core.dom.FieldDeclaration;

public class DeclarationVisitor extends ASTVisitor {
	private String pkgName, className, methodName;
	private ProgramElement pkgElem;
	private TypeElement classElem;
	private MethodElement methodElem;
	private CompilationUnit compilationUnit;

	public DeclarationVisitor(CompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}

	@Override
	public boolean visit(PackageDeclaration pkgDecl) {
		this.pkgName = pkgDecl.getName().getFullyQualifiedName();
		this.pkgElem = new ProgramElement(pkgName);
		ProgramElement prePkg = ModelProviderProgElem.INSTANCE.addProgramElement(this.pkgElem);
		if (prePkg != null) {
			this.pkgElem = prePkg;
		}
		return super.visit(pkgDecl);
	}

	/**
	 * A type declaration is the union of a class declaration and an interface
	 * declaration.
	 */
	@Override
	public boolean visit(TypeDeclaration typeDecl) {
		this.className = typeDecl.getName().getIdentifier();
		this.classElem = new TypeElement(className, this.pkgElem);
		this.classElem.setFields(typeDecl.getFields());
		System.out.println(this.classElem.getFieldsStr());
		System.out.println(this.classElem.getNumFields());
		List<?> fieldDecls = this.classElem.getFields();
		for (Object object : fieldDecls) {
			if (object instanceof FieldDeclaration) {
				FieldDeclaration fieldDecl = (FieldDeclaration) object;
				System.out.println(fieldDecl.getType());
				System.out.println(fieldDecl.fragments());
				System.out.println(fieldDecl.getStartPosition());
				// System.out.println(fieldDecl.getModifiers());
			}
		}
		this.pkgElem.add(classElem);
		return super.visit(typeDecl);
	}

	@Override
	public boolean visit(MethodDeclaration methodDecl) {
		this.methodName = methodDecl.getName().getIdentifier();
		this.methodElem = new MethodElement(methodName, this.classElem);
		this.methodElem.setParameters(methodDecl.parameters());

		String className = methodDecl.resolveBinding().getDeclaringClass().getName();
		this.methodElem.setClassName(className);

		String pkgName = methodDecl.resolveBinding().getDeclaringClass().getPackage().getName();
		this.methodElem.setPkgName(pkgName);

		this.methodElem.setStartPos(methodDecl.getStartPosition());
		
		this.methodElem.setStartLine(getLineNum(compilationUnit, methodDecl));

		int methodModifers = methodDecl.getModifiers();
		boolean isPublic = (methodModifers & Modifier.PUBLIC) != 0;
		this.methodElem.setModifierPublic(isPublic);

		this.classElem.add(methodElem);
		return super.visit(methodDecl);
	}

	int getLineNum(CompilationUnit compilationUnit, ASTNode node) {
		return compilationUnit.getLineNumber(node.getStartPosition());
	}
}
