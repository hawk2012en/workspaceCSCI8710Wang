/**
 * @(#) ReplaceMethodVisitor.java
 */
package visitor.rewrite;

import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class InsertMethodBodyVisitor extends ASTVisitor {
	private ProgramElement curProgElem;
	private String newStatements;	

	private ASTRewrite rewrite;
	private AST astCUnit;

	public InsertMethodBodyVisitor(AST astCUnit, ASTRewrite rewrite) {
		this.astCUnit = astCUnit;
		this.rewrite = rewrite;
	}

	public InsertMethodBodyVisitor(ProgramElement curProgElem, String newStatements) {
		this.curProgElem = curProgElem;
		this.newStatements = newStatements;	
	}

	public boolean visit(MethodDeclaration node) {
		if (checkMethod(node)) {
			ASTParser parser = ASTParser.newParser(AST.JLS11);
			parser.setSource(newStatements.toCharArray());
			parser.setKind(ASTParser.K_STATEMENTS);
			Block block = (Block) parser.createAST(null);			
			Block oldBlock = node.getBody();			
			Block newBlock = astCUnit.newBlock();
			ListRewrite listRewrite = rewrite.getListRewrite(newBlock, Block.STATEMENTS_PROPERTY);
			List<?> curBodyStmts = oldBlock.statements();
			for (Object stmt : curBodyStmts) {
				listRewrite.insertLast((ASTNode) stmt, null);
			}					
			List<?> bodyStmts = block.statements();
			for (Object stmt : bodyStmts) {
				listRewrite.insertLast((ASTNode) stmt, null);
			}
			rewrite.replace(oldBlock, newBlock, null);
			
		}
		return true;
	}

	private boolean checkMethod(MethodDeclaration md) {
		boolean check1 = this.curProgElem.getMethodName().equals(md.getName().getFullyQualifiedName());
		boolean check2 = this.curProgElem.getParameterSize().equals(md.parameters().size());
		if (check1 && check2) {
			return true;
		}
		return false;
	}

	public void setAST(AST ast) {
		this.astCUnit = ast;
	}

	public void ASTRewrite(ASTRewrite rewrite) {
		this.rewrite = rewrite;
	}
}
