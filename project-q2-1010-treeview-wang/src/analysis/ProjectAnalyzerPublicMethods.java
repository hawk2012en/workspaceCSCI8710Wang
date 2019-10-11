package analysis;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import util.UtilAST;
import visitor.DeclarationVisitor;
import visitor.DeclarationVisitorPublicMethod;

public class ProjectAnalyzerPublicMethods extends ProjectAnalyzer {

	public ProjectAnalyzerPublicMethods() {
		super();
	}
	
	@Override
	protected void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
		      // =============================================================
		      // 3rd step: ICompilationUnits
		      // =============================================================
		      for (ICompilationUnit iUnit : iCompilationUnits) {
		         CompilationUnit compilationUnit = UtilAST.parse(iUnit);
		         DeclarationVisitorPublicMethod declVisitor = new DeclarationVisitorPublicMethod();
		         compilationUnit.accept(declVisitor);
		      }
		   }
}
