package simpleVisitorPattern.part;

import java.util.ArrayList;
import java.util.List;

import simpleVisitorPattern.visitor.CartPartVisitor;

public class Engine implements ICarElement {
	String name;
	String modelNumberEngine;
	String modelYearEngine;
	List<ICarElement> children = new ArrayList<ICarElement>();

	public Engine(String n) {
		this.name = n;
	}

	public void accept(CartPartVisitor visitor) {
		if (visitor.visit(this) && children.size() > 0) {
			for (ICarElement child : children) {
				if(child instanceof Wheel) {
					Wheel child2 = (Wheel) child;
					visitor.visit(child2);
				}				
				if(child instanceof Body) {
					Body child2 = (Body) child;
					visitor.visit(child2);
				}
				if(child instanceof Engine) {
					Engine child2 = (Engine) child;
					visitor.visit(child2);
				}
				if(child instanceof Break) {
					Break child2 = (Break) child;
					visitor.visit(child2);
				}
			}
		}
	}

	public List<ICarElement> getChildren() {
		return children;
	}

	public void addChild(ICarElement part) {
		children.add(part);
	}
	
	public void removeChild(ICarElement part) {
		children.remove(part);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModelNumberEngine() {
		return modelNumberEngine;
	}

	public void setModelNumberEngine(String modelNumberEngine) {
		this.modelNumberEngine = modelNumberEngine;
	}

	public String getModelYearEngine() {
		return modelYearEngine;
	}

	public void setModelYearEngine(String modelYearEngine) {
		this.modelYearEngine = modelYearEngine;
	}
}