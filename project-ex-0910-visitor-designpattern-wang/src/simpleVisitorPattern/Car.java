package simpleVisitorPattern;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import simpleVisitorPattern.part.Break;
import simpleVisitorPattern.visitor.CartPartVisitor;

class Car {
	Wheel wheel = new Wheel("Wheel Part");
	Body body = new Body("Body Part");
	Engine engine = new Engine("Engine Part");
	Break break1 = new Break("Break Part");

	public void addChild() {
		wheel.addChild(break1);
		body.addChild(engine);
	}

	public void accept(CartPartVisitor visitor) {

		wheel.accept(visitor);
		// engine.accept(visitor);
		body.accept(visitor);
		// break1.accept(visitor);
	}
}