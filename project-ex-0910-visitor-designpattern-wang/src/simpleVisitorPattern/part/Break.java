package simpleVisitorPattern.part;

import java.util.ArrayList;
import java.util.List;

import simpleVisitorPattern.visitor.CartPartVisitor;

public class Break implements ICarElement {
	String name;
	String modelNumberBreak;
	String modelYearBreak;
	List<ICarElement> children = new ArrayList<ICarElement>();

	public Break(String n) {
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

	public String getModelNumberBreak() {
		return modelNumberBreak;
	}

	public void setModelNumberBreak(String modelNumberBreak) {
		this.modelNumberBreak = modelNumberBreak;
	}

	public String getModelYearBreak() {
		return modelYearBreak;
	}

	public void setModelYearBreak(String modelYearBreak) {
		this.modelYearBreak = modelYearBreak;
	}
}