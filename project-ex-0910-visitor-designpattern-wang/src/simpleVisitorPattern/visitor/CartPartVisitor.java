package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;

public abstract class CartPartVisitor {
//   public abstract void visit(Wheel wheel);   
//   public abstract void visit(Engine engine);
//   public abstract void visit(Body body);
//   public abstract void visit(Break break1);   
	public boolean visit(Wheel wheel) {
		return true;
	};

	public boolean visit(Engine engine) {
		return true;
	};

	public boolean visit(Body body) {
		return true;
	};

	public boolean visit(Break break1) {
		return true;
	};
}