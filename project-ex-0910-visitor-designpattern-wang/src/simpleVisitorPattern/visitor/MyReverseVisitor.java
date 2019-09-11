package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;

public class MyReverseVisitor extends CartPartVisitor {
	public void visit(Wheel part) {
		String oldName = part.getName(); // Get old name.
		StringBuilder newName = new StringBuilder();
		newName = newName.append(oldName).reverse(); // reverse name
		part.setName(newName.toString());
		
		String oldModelNumber = part.getModelNumberWheel();
		StringBuilder newModelNumber = new StringBuilder();
		newModelNumber = newModelNumber.append(oldModelNumber).reverse(); 
		part.setModelNumberWheel(newModelNumber.toString());
		
		String oldModelYear = part.getModelYearWheel();
		StringBuilder newModelYear = new StringBuilder();
		newModelYear = newModelYear.append(oldModelYear).reverse(); 
		part.setModelYearWheel(newModelYear.toString());
	}

	public void visit(Engine part) {
		String oldName = part.getName(); // Get old name.
		StringBuilder newName = new StringBuilder();
		newName = newName.append(oldName).reverse(); // reverse name
		part.setName(newName.toString());
		
		String oldModelNumber = part.getModelNumberEngine();
		StringBuilder newModelNumber = new StringBuilder();
		newModelNumber = newModelNumber.append(oldModelNumber).reverse(); 
		part.setModelNumberEngine(newModelNumber.toString());
		
		String oldModelYear = part.getModelYearEngine();
		StringBuilder newModelYear = new StringBuilder();
		newModelYear = newModelYear.append(oldModelYear).reverse(); 
		part.setModelYearEngine(newModelYear.toString());
	}

	public void visit(Body part) {
		String oldName = part.getName(); // Get old name.
		StringBuilder newName = new StringBuilder();
		newName = newName.append(oldName).reverse(); // reverse name
		part.setName(newName.toString());
		
		String oldModelNumber = part.getModelNumberBody();
		StringBuilder newModelNumber = new StringBuilder();
		newModelNumber = newModelNumber.append(oldModelNumber).reverse(); 
		part.setModelNumberBody(newModelNumber.toString());
		
		String oldModelYear = part.getModelYearBody();
		StringBuilder newModelYear = new StringBuilder();
		newModelYear = newModelYear.append(oldModelYear).reverse(); 
		part.setModelYearBody(newModelYear.toString());
	}

	public void visit(Break part) {
		String oldName = part.getName(); // Get old name.
		StringBuilder newName = new StringBuilder();
		newName = newName.append(oldName).reverse(); // reverse name
		part.setName(newName.toString());
		
		String oldModelNumber = part.getModelNumberBreak();
		StringBuilder newModelNumber = new StringBuilder();
		newModelNumber = newModelNumber.append(oldModelNumber).reverse(); 
		part.setModelNumberBreak(newModelNumber.toString());
		
		String oldModelYear = part.getModelYearBreak();
		StringBuilder newModelYear = new StringBuilder();
		newModelYear = newModelYear.append(oldModelYear).reverse(); 
		part.setModelYearBreak(newModelYear.toString());
	}
}