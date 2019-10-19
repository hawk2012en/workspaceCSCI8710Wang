package model.provider;

import org.eclipse.jface.viewers.StyledString;

import model.progelement.FieldElement;
import model.progelement.MethodElement;

public class StartPosLabelProvider extends LabelProviderProgElem {

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof MethodElement) {
			return new StyledString(String.valueOf(((MethodElement) element).getStartPos()));
		}
		else if (element instanceof FieldElement) {
			return new StyledString(String.valueOf(((FieldElement) element).getStartPos()));
		}
		return new StyledString(""); // super.getStyledText(element);
	}
}
