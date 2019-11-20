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
import view.SimpleViewer;

/**
 * @since JavaSE-1.8
 */
public class DefUseHandler {
   final String viewId = "project-1121-q3-wang.partdescriptor.simpleviewdef-usecallee-caller";

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
      
      if (part != null && part.getObject() instanceof SimpleViewer) {
         ProjectAnalyzerDefUse analyzer = new ProjectAnalyzerDefUse(dialog.getVariableName(), dialog.getMethodName(), dialog.getClassName());
         try {
            analyzer.analyze();
         } catch (CoreException e) {
            e.printStackTrace();
         }
         List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();
         SimpleViewer viewer = (SimpleViewer) part.getObject();
         displayDefUsedView(viewer, analysisDataList);
      }
   }

   private void displayDefUsedView(SimpleViewer viewer, List<Map<IVariableBinding, DefUseModel>> analysisDataList) {      
	  viewer.reset();
	  int counter = 1;
      for (Map<IVariableBinding, DefUseModel> iMap : analysisDataList) {
         for (Entry<IVariableBinding, DefUseModel> entry : iMap.entrySet()) {
            IVariableBinding iBinding = entry.getKey();
            DefUseModel iVariableAnal = entry.getValue();
            CompilationUnit cUnit = iVariableAnal.getCompilationUnit();
            VariableDeclarationStatement varDeclStmt = iVariableAnal.getVarDeclStmt();
            VariableDeclarationFragment varDecl = iVariableAnal.getVarDeclFrgt();

            System.out.print("[" + (counter) + "] ABOUT VARIABLES '" + varDecl.getName() + "'\n");
            viewer.appendText("[" + (counter++) + "] ABOUT VARIABLES '" + varDecl.getName() + "'\n");            
            String method = "[METHOD] " + iBinding.getDeclaringMethod() + "\n";
            viewer.appendText(method);
            System.out.print(method);            
            String stmt = "\t[DECLARE STMT] " + strTrim(varDeclStmt) + "\t [" + getLineNum(cUnit, varDeclStmt) + "]\n";
            viewer.appendText(stmt);
            System.out.print(stmt);
            String var = "\t[DECLARE VAR] " + varDecl.getName() + "\t [" + getLineNum(cUnit, varDecl) + "]\n";
            viewer.appendText(var);
            System.out.print(var);

            List<SimpleName> usedVars = iVariableAnal.getUsedVars();
            for (SimpleName iSimpleName : usedVars) {

               ASTNode parentNode = iSimpleName.getParent();
               if (parentNode != null && parentNode instanceof Assignment) {
                  String assign = "\t\t[ASSIGN VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName) + "]\n";
                  viewer.appendText(assign);
                  System.out.print(assign);
               } else {
                  String use = "\t\t[USE VAR] " + strTrim(parentNode) + "\t [" + getLineNum(cUnit, iSimpleName) + "]\n";
                  viewer.appendText(use);
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