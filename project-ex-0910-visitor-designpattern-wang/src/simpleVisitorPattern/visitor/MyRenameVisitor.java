package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;

public class MyRenameVisitor extends CartPartVisitor {
   
   @Override
   public boolean visit(Wheel part) {
      String newName = "New " + part.getName(); // Suppose a new name is notified.
      // System.out.println("[DBG] Changing the name property to : " + newName);
      part.setName(newName);
      //return super.visit(part);
      return false;
   }

   @Override
   public boolean visit(Engine part) {
      String newName = "New " + part.getName(); // Suppose a new name is notified.
      // System.out.println("[DBG] Changing the name property to : " + newName);
      part.setName(newName);
      return super.visit(part);
   }

   @Override
   public boolean visit(Body part) {
      String newName = "New " + part.getName(); // Suppose a new name is notified.
      // System.out.println("[DBG] Changing the name property to : " + newName);
      part.setName(newName);
      //return super.visit(part);
      return false;
   }
   
   @Override
   public boolean visit(Break part) {
	      String newName = "New " + part.getName(); // Suppose a new name is notified.
	      // System.out.println("[DBG] Changing the name property to : " + newName);
	      part.setName(newName);
	      return super.visit(part);
   }
}