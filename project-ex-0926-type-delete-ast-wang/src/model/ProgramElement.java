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
	private boolean isModifierPublic;

	public ProgramElement(String pkgName, String className, String methodName, boolean isRetVoid, int parmSize) {
		this.pkgName = pkgName;
		this.className = className;
		this.methodName = methodName;
		this.isReturnVoid = isRetVoid;
		this.parameterSize = parmSize;
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

	public boolean isModifierPublic() {
		return isModifierPublic;
	}

	public void setModifierPublic(boolean isModifierPublic) {
		this.isModifierPublic = isModifierPublic;
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
