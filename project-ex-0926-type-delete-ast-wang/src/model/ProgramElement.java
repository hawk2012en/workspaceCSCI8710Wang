/**
 * @(#) ProgramElement.java
 */
package model;

/**
 * @since J2SE-1.8
 */
public class ProgramElement {
	private String pkgName;
	private String className;
	private String methodName;
	private boolean isReturnVoid;
	private Integer parameterSize;
	private boolean isModifierPrivate;

	public ProgramElement(String pkgName, String className, String methodName, boolean isRetVoid, int parmSize, boolean isModifierPrivate) {
		this.pkgName = pkgName;
		this.className = className;
		this.methodName = methodName;
		this.isReturnVoid = isRetVoid;
		this.parameterSize = parmSize;
		this.isModifierPrivate = isModifierPrivate;
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

	public boolean isModifierPrivate() {
		return isModifierPrivate;
	}

	public void setModifierPrivate(boolean isModifierPrivate) {
		this.isModifierPrivate = isModifierPrivate;
	}

	public Integer getParameterSize() {
		return parameterSize;
	}

	public void setParameterSize(Integer parameterSize) {
		this.parameterSize = parameterSize;
	}

	@Override
	public String toString() {
		String isVoid = isReturnVoid ? "Yes" : "No";
		return pkgName + ", " + className + ", " + methodName + ", " + isVoid + ", " + parameterSize;
	}
}
