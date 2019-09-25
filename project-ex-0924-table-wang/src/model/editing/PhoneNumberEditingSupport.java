package model.editing;

import org.eclipse.jface.viewers.TableViewer;

import model.Person;

public class PhoneNumberEditingSupport extends FirstNameEditingSupport {
   private final TableViewer viewer;

   public PhoneNumberEditingSupport(TableViewer viewer) {
      super(viewer);
      this.viewer = viewer;
   }

   @Override
   protected Object getValue(Object element) {
      return ((Person) element).getPhoneNumber();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ((Person) element).setPhoneNumber(String.valueOf(value));
      viewer.update(element, null);
   }
}
