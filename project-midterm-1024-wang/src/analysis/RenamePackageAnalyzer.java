package analysis;
/*
 * @(#) ASTAnalyzer.java
 *
 * Copyright 2015-2018 The Software Analysis Laboratory
 * Computer Science, The University of Nebraska at Omaha
 * 6001 Dodge Street, Omaha, NE 68182.
 */

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.refactoring.IJavaRefactorings;
import model.progelement.ProgramElement;
import util.UtilAST;

public class RenamePackageAnalyzer {
   final String JAVANATURE = "org.eclipse.jdt.core.javanature";
   private ProgramElement curPackageElem;
   private String newPackageName;

   public RenamePackageAnalyzer(ProgramElement packageElem, String newPackageName) {
      this.curPackageElem = packageElem;
      this.newPackageName = newPackageName;
   }

   public void analyze() throws CoreException {
      // =============================================================
      // 1st step: Project
      // =============================================================
      IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
      for (IProject project : projects) {
         if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) {
            continue;
         }
         analyzePackages(JavaCore.create(project).getPackageFragments());
      }
   }

   protected void analyzePackages(IPackageFragment[] packages) throws CoreException, JavaModelException {
      // =============================================================
      // 2nd step: Packages
      // =============================================================
      for (IPackageFragment iPackage : packages) {
         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE && //
               iPackage.getCompilationUnits().length >= 1 && //
               iPackage.getElementName().equals(curPackageElem.getName())) {
        	 UtilAST.rename(iPackage, this.newPackageName, IJavaRefactorings.RENAME_PACKAGE);
         }
      }
   }
}
