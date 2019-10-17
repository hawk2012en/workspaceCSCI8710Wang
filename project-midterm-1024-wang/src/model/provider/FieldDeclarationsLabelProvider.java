package model.provider;

import org.eclipse.jface.viewers.StyledString;
import model.progelement.TypeElement;

public class FieldDeclarationsLabelProvider extends LabelProviderProgElem {

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof TypeElement) {
			return new StyledString(((TypeElement) element).getFieldsStr());
		}
		return new StyledString(""); // super.getStyledText(element);
	}
}
