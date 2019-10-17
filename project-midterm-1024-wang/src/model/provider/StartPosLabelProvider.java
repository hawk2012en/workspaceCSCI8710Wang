package model.provider;

import org.eclipse.jface.viewers.StyledString;

import model.progelement.MethodElement;

public class StartPosLabelProvider extends LabelProviderProgElem {

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof MethodElement) {
			return new StyledString(String.valueOf(((MethodElement) element).getStartPos()));
		}
		return new StyledString(""); // super.getStyledText(element);
	}
}
