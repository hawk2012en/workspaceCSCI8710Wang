package model;

public class ProgramElement {
	private String		pkgName;
	private String		className;
	private String		methodName;
	private boolean	isReturnVoid;
	private boolean	isPublicModifier;
	

	public ProgramElement() {
	}

	public ProgramElement(String pkgName, String className, String methodName, boolean isRetVoid, boolean isPublicModifier) {
		this.pkgName = pkgName;
		this.className = className;
		this.methodName = methodName;
		this.isReturnVoid = isRetVoid;
		this.isPublicModifier = isPublicModifier;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public boolean isReturnVoid() {
		return isReturnVoid;
	}

	public void setReturnVoid(boolean isReturnVoid) {
		this.isReturnVoid = isReturnVoid;
	}

	public boolean isPublicModifier() {
		return isPublicModifier;
	}

	public void setPublicModifier(boolean isPublicModifier) {
		this.isPublicModifier = isPublicModifier;
	}

	@Override
	public String toString() {
		String isVoid = isReturnVoid ? "Yes" : "No";
		String isPublic = isPublicModifier ? "Yes" : "No";
		return pkgName + ", " + className + ", " + methodName + ", " + isVoid + ", " + isPublic ;
	}
}
