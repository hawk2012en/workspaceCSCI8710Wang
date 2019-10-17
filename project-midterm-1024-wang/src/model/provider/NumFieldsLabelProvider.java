package model.provider;

import org.eclipse.jface.viewers.StyledString;
import model.progelement.TypeElement;

public class NumFieldsLabelProvider extends LabelProviderProgElem {

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof TypeElement) {
			return new StyledString(String.valueOf(((TypeElement) element).getNumFields()));
		}
		return new StyledString(""); // super.getStyledText(element);
	}
}
