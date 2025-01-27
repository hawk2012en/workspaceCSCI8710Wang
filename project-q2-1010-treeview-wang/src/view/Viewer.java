
package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import analysis.ProjectAnalyzer;
import analysis.ProjectAnalyzerPublicMethods;
import model.MethodElement;
import model.ProgElementModelProvider;
import model.ProgramElement;
import util.MsgUtil;
import util.UtilFile;
import view.provider.MethodLabelProvider;
import view.provider.ProgElemContentProvider;
import view.provider.ProgElemLabelProvider;
import view.provider.StartPosLabelProvider;

public class Viewer {
	public static final String ID = "simpletreeviewerastexample.partdescriptor.simpleasttreeview";
	private TreeViewer viewer;	
	private int level;
	private List<String> contents = new ArrayList<String>();

	@PostConstruct
	public void postConstruct(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		createProgElemColumns();
		createPopupMenu(parent);
		addListener();
	}

	private void createProgElemColumns() {
		String[] titles = { "Program Element", "Method Parameter", "Location" };
		int[] bounds = { 300, 100, 100 };

		viewer.setContentProvider(new ProgElemContentProvider());
		viewer.getTree().setHeaderVisible(true);

		// the first
		TreeViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new DelegatingStyledCellLabelProvider(new ProgElemLabelProvider()));

		// the second
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new DelegatingStyledCellLabelProvider(new MethodLabelProvider()));

		// the third
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new DelegatingStyledCellLabelProvider(new StartPosLabelProvider()));
	}

	private TreeViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private void createPopupMenu(Composite parent) {
		Menu contextMenu = new Menu(viewer.getTree());
		viewer.getTree().setMenu(contextMenu);
		createMenuItem(contextMenu);
		createMenuItem2(contextMenu);
		createMenuItem3(contextMenu);
		createMenuItem4(contextMenu);
	}
	
	private void createMenuItem(Menu parent) {
		final MenuItem menuItem = new MenuItem(parent, SWT.PUSH);
		menuItem.setText("Analyze Program Wang");
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateViewProgramAnalysis();
				MsgUtil.openInfo("Method Count", "Info: Analyzed " + ProgElementModelProvider.INSTANCE.methodCount + " methods in total.");				
			}
		});
	}
	
	public void updateViewProgramAnalysis() {
		ProjectAnalyzer analyzer = new ProjectAnalyzer();
		ProgElementModelProvider.INSTANCE.methodCount = 0;
		ProgElementModelProvider.INSTANCE.classCount = 0;
		ProgElementModelProvider.INSTANCE.clearProgramElements();
		analyzer.analyze();

		List<ProgramElement> data = ProgElementModelProvider.INSTANCE.getProgElements();
		ProgramElement[] array = data.toArray(new ProgramElement[data.size()]);

		viewer.getTree().deselectAll();
		viewer.setInput(array);
	}
	
	private void createMenuItem2(Menu contextMenu) {
		final MenuItem menuItem = new MenuItem(contextMenu, SWT.PUSH);
		menuItem.setText("Export");
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				contents.clear();
				List<ProgramElement> progElements = ProgElementModelProvider.INSTANCE.getProgElements();
				for (ProgramElement progElement : progElements) {					
					level = 0;
					printProgElement(progElement);
				}
				String filePath = UtilFile.getOutputPathString();
				try {
					// UtilFile.saveFile("D:\\output.csv", contents);
					UtilFile.saveFile(filePath, contents);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int linesExported = contents.size();
				MsgUtil.openInfo("Export", "Info: Exported " + linesExported + " lines.");
			}
		});
	}

	private void printProgElement(ProgramElement parent) {
		String tab = "";
		for(int i = 0; i < level; i++) {
			tab += "\t";
		}
		if (parent instanceof MethodElement) {
			MethodElement method = (MethodElement) parent;
//			System.out.println(tab + method + ":" + method.getParent() + " (" + method.getParameterStr() + ") "
//					+ String.valueOf(method.getStartPos()));
			contents.add(tab + method + " (" + method.getParameterStr() + ") "
					+ String.valueOf(method.getStartPos()));
		} 
		else {
//			System.out.println(tab + parent + ":" + parent.getParent());			
			contents.add(tab + parent + "(" + parent.list().length +")");			
		}
		if (parent.hasChildren()) {			
			level++;
			for (ProgramElement child : parent.list()) {
				printProgElement(child);
			}
			level--;
		}
	}

	private void createMenuItem3(Menu parent) {
		final MenuItem menuItem = new MenuItem(parent, SWT.PUSH);
		menuItem.setText("Clear Tree View Wang");
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				viewer.getTree().deselectAll();
				ProgElementModelProvider.INSTANCE.clearProgramElements();
				List<ProgramElement> data = ProgElementModelProvider.INSTANCE.getProgElements();
				ProgramElement[] array = data.toArray(new ProgramElement[data.size()]);				
				viewer.setInput(array);
				MsgUtil.openInfo("Class Count", "Info: Removed " + ProgElementModelProvider.INSTANCE.classCount + " classes in total.");				
			}
		});
	}
	
	private void createMenuItem4(Menu parent) {
		final MenuItem menuItem = new MenuItem(parent, SWT.PUSH);
		menuItem.setText("Analyze Program and Show Only Public Method Wang");
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ProgElementModelProvider.INSTANCE.methodCount = 0;
				ProgElementModelProvider.INSTANCE.classCount = 0;
				ProjectAnalyzerPublicMethods analyzer = new ProjectAnalyzerPublicMethods();
				ProgElementModelProvider.INSTANCE.clearProgramElements();
				analyzer.analyze();

				List<ProgramElement> data = ProgElementModelProvider.INSTANCE.getProgElements();
				ProgramElement[] array = data.toArray(new ProgramElement[data.size()]);

				viewer.getTree().deselectAll();
				viewer.setInput(array);
				MsgUtil.openInfo("Public Method Count", "Info: Analyzed " + ProgElementModelProvider.INSTANCE.methodCount + " public methods in total.");				
			}
		});
	}	

	private void addListener() {
		Tree tree = (Tree) viewer.getControl();
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeViewer viewer = (TreeViewer) event.getViewer();
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				viewer.setExpandedState(selectedNode, !viewer.getExpandedState(selectedNode));
			}
		});

		tree = (Tree) viewer.getControl();
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem treeItem = (TreeItem) event.item;
				final TreeColumn[] treeColumns = treeItem.getParent().getColumns();
				viewer.getTree().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						for (TreeColumn treeColumn : treeColumns)
							treeColumn.pack();
					}
				});
			}
		};
		tree.addListener(SWT.Expand, listener);
	}
	
	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public TreeViewer getViewer() {
		return this.viewer;
	}
}