package model.progelement;

import java.util.List;

public class FieldElement extends ProgramElement {
	private List<?> fragments;	
	private int startPos;
	private int startLine;
	private String type;

	public FieldElement(String name, ProgramElement parent) {
		super(name, parent);
	}

	public void setFragments(List<?> fragments) {
		this.fragments = fragments;
	}

	public List<?> getFragments() {
		return fragments;
	}

	public String getFieldDefinition() {
		return name;
	}

	public Integer getFragmentSize() {
		return fragments.size();
	}

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getStartLine() {
		return startLine;
	}

	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
