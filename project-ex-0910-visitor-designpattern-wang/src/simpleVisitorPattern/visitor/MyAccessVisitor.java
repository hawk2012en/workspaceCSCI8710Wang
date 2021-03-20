package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;

public class MyAccessVisitor extends CartPartVisitor {
	@Override
	public boolean visit(Wheel part) {
		System.out.println("[DBG] name: " + part.getName() + ", model: " + part.getModelNumberWheel() + ", year: " + part.getModelYearWheel());
		return super.visit(part);
		//return false;
	}

	@Override
	public boolean visit(Engine part) {
		System.out.println("[DBG] name: " + part.getName() + ", model: " + part.getModelNumberEngine() + ", year: " + part.getModelNumberEngine());
		return super.visit(part);

	}

	@Override
	public boolean visit(Body part) {
		System.out.println("[DBG] name: " + part.getName() + ", model: " + part.getModelNumberBody() + ", year: " + part.getModelYearBody());
		return super.visit(part);
		//return false;
	}
	
	@Override
	public boolean visit(Break part) {
		System.out.println("[DBG] name: " + part.getName() + ", model: " + part.getModelNumberBreak() + ", year: " + part.getModelYearBreak());
		return super.visit(part);
	}
}