package model.labelprovider;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Text;

import model.Person;

public class EmailLabelProvider extends FirstNameLabelProvider {

   public EmailLabelProvider(Text searchText) {
      super(searchText);
   }

   @Override
   public void update(ViewerCell cell) {
      super.update(cell);
   }

   protected String getCellText(ViewerCell cell) {
      Person person = (Person) cell.getElement();
      String cellText = person.getEmail();
      return cellText;
   }
}

/*
  public class FirstNameLabelProvider extends ColumnLabelProvider {
	@Override
	public String getText(Object element) {
		Person p = (Person) element;
		return p.getLastName();
	}
}
*/