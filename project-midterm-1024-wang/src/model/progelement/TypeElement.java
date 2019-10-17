package model.progelement;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.FieldDeclaration;

public class TypeElement extends ProgramElement {
	private String pkgName;
	private List<?> fields;

	public TypeElement(String name, ProgramElement parent) {
		super(name, parent);
		this.pkgName = parent.getName();
	}

	public String getPkgName() {
		return pkgName;
	}

	public List<?> getFields() {
		return fields;
	}

	public void setFields(FieldDeclaration[] f) {
		List<?> list = Arrays.asList(f);
		this.fields = list;
	}

	public String getFieldsStr() {
		if (fields.isEmpty()) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (Object t : fields) {
			buf.append(t.toString().substring(0, t.toString().length() - 1) + " ");
		}
		return buf.toString().trim().substring(0, buf.toString().trim().length());
	}
	
	public Integer getNumFields() {
		return fields.size();
	}

}
