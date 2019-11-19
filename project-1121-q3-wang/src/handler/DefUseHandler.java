/**
 * @file DefUseHandler.java
 */
package handler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.swt.widgets.Shell;

import analysis.ProjectAnalyzerDefUse;
import model.DefUseModel;
import view.DefUseDialog;

/**
 * @since JavaSE-1.8
 */
public class DefUseHandler {
   final String viewId = "simplebindingproject.partdescriptor.simplebindingview";

   @Inject
   private EPartService service;
   @Inject
   @Named(IServiceConstants.ACTIVE_SHELL)
   Shell shell;
   
   @Execute
   public void execute() {
	   DefUseDialog dialog = new DefUseDialog(shell);
	   dialog.open();
//	   System.out.println("Variable Name: " + dialog.getVariableName());
//	   System.out.println("Method Name: " + dialog.getMethodName());
//	   System.out.println("Class Name: " + dialog.getClassName());
	      
      MPart part = service.findPart(viewId);

         ProjectAnalyzerDefUse analyzer = new ProjectAnalyzerDefUse(dialog.getVariableName(), dialog.getMethodName(), dialog.getClassName());
         try {
            analyzer.analyze();
         } catch (CoreException e) {
            e.printStackTrace();
         }
         List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();

         displayDefUsedView(analysisDataList);
   }

   private void displayDefUsedView(List<Map<IVariableBinding, DefUseModel>> analysisDataList) {      
      int counter = 1;
      for (Map<IVariableBinding, DefUseModel> iMap : analysisDataList) {
         for (Entry<IVariableBinding, DefUseModel> entry : iMap.entrySet()) {
            IVariableBinding iBinding = entry.getKey();
            DefUseModel iVariableAnal = entry.getValue();
            CompilationUnit cUnit = iVariableAnal.getCompilationUnit();
            VariableDeclarationStatement varDeclStmt = iVariableAnal.getVarDeclStmt();
            VariableDeclarationFragment varDecl = iVariableAnal.getVarDeclFrgt();

            System.out.print("[" + (counter++) + "] ABOUT VARIABLES '" + varDecl.getName() + "'\n");
            String method = "[METHOD] " + iBinding.getDeclaringMethod() + "\n";
            System.out.print(method);
            String stmt = "\t[DECLARE STMT] " + strTrim(varDeclStmt) + "\t [" + getLineNum(cUnit, varDeclStmt) + "]\n";
            System.out.print(stmt);
            String var = "\t[DECLARE VAR] " + varDecl.getName() + "\t [" + getLineNum(cUnit, varDecl) + "]\n";
            System.out.print(var);

            List<SimpleName> usedVars = iVariableAnal.getUsedVars();
            for (SimpleName iSimpleName : usedVars) {

               ASTNode parentNode = iSimpleName.getParent();
               if (parentNode != null && parentNode instanceof Assignment) {
                  String assign = "\t\t[ASSIGN VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName) + "]\n";
                  System.out.print(assign);
               } else {
                  String use = "\t\t[USE VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName) + "]\n";
                  System.out.print(use);
               }
            }
         }
      }
   }

   String strTrim(Object o) {
      return o.toString().trim();
   }

   int getLineNum(CompilationUnit compilationUnit, ASTNode node) {
      return compilationUnit.getLineNumber(node.getStartPosition());
   }
}