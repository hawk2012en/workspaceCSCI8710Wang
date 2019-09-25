package model.editing;

import org.eclipse.jface.viewers.TableViewer;

import model.Person;

public class EmailEditingSupport extends FirstNameEditingSupport {
   private final TableViewer viewer;

   public EmailEditingSupport(TableViewer viewer) {
      super(viewer);
      this.viewer = viewer;
   }

   @Override
   protected Object getValue(Object element) {
      return ((Person) element).getEmail();
   }

   @Override
   protected void setValue(Object element, Object value) {
      ((Person) element).setEmail(String.valueOf(value));;
      viewer.update(element, null);
   }
}
