package handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import view.MyView;

public class MyHandler extends AbstractHandler {
   final private String VIEWID = "view.MyView";

   @Override
   public Object execute(ExecutionEvent event) throws ExecutionException {
      IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
      IViewPart findView = window.getActivePage().findView(VIEWID);
      if (findView instanceof MyView) {
         MyView myView = (MyView) findView;
         myView.appendText("Hello World!\n");
      }

      return null;
   }
}
