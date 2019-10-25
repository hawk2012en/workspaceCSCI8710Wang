package analysis;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import util.UtilAST;
import visitor.DeclarationVisitorPrivateMethod;

public class ProjectAnalyzerPrivateMethods extends ProjectAnalyzer {

	public ProjectAnalyzerPrivateMethods() {
		super();
	}
	
	@Override
	protected void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
	      // =============================================================
	      // 3rd step: ICompilationUnits
	      // =============================================================
	      for (ICompilationUnit iUnit : iCompilationUnits) {
	         CompilationUnit compilationUnit = UtilAST.parse(iUnit);
	         DeclarationVisitorPrivateMethod declVisitor = new DeclarationVisitorPrivateMethod();
	         compilationUnit.accept(declVisitor);
	      }
	   }
}
