package simpleVisitorPattern.part;

import java.util.ArrayList;
import java.util.List;

import simpleVisitorPattern.visitor.CartPartVisitor;

//public class Wheel implements ICarElement {
public class Wheel {
	String name;
	String modelNumberWheel;
	String modelYearWheel;
	List<ICarElement> children = new ArrayList<ICarElement>();

	public Wheel(String n) {
		this.name = n;
	}

	public void accept(CartPartVisitor visitor) {
		if (visitor.visit(this) && children.size() > 0) {
			for (ICarElement child : children) {
				if(child instanceof Wheel) {
					Wheel child2 = (Wheel) child;
					visitor.visit(child2);
					continue;
				}				
				if(child instanceof Body) {
					Body child2 = (Body) child;
					visitor.visit(child2);
					continue;
				}
				if(child instanceof Engine) {
					Engine child2 = (Engine) child;
					visitor.visit(child2);
					continue;
				}
				if(child instanceof Break) {
					Break child2 = (Break) child;
					visitor.visit(child2);
					continue;
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

	public String getModelNumberWheel() {
		return modelNumberWheel;
	}

	public void setModelNumberWheel(String modelNumberWheel) {
		this.modelNumberWheel = modelNumberWheel;
	}

	public String getModelYearWheel() {
		return modelYearWheel;
	}

	public void setModelYearWheel(String modelYearWheel) {
		this.modelYearWheel = modelYearWheel;
	}
}