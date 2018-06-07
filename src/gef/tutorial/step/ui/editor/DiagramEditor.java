package gef.tutorial.step.ui.editor;

import java.util.ArrayList;
import java.util.EventObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import gef.tutorial.step.app.Application;
import gef.tutorial.step.constant.IImageKeys;
import gef.tutorial.step.editpart.PartFactory;
import gef.tutorial.step.editpart.tree.TreeEditPartFactory;
import gef.tutorial.step.model.ArrowConnectionModel;
import gef.tutorial.step.model.ContentsModel;
import gef.tutorial.step.model.HelloModel;
import gef.tutorial.step.model.LineConnectionModel;

public class DiagramEditor extends GraphicalEditorWithPalette {

	// Editor ID
	public static final String ID = "gef.tutorial.step.ui.DiagramEditor";
	// an EditDomain is a "session" of editing which contains things like the
	// CommandStack
	GraphicalViewer viewer;
	private ContentsModel contentsModel = null;

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		viewer = getGraphicalViewer();
		// ��������������ScalableRootEditPart
		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		ZoomManager manager = rootEditPart.getZoomManager();
		// �Ŵ��������
		double[] zoomLevels = new double[] {
				// ���ű����Ǵ�25����2000��
				0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0, 20.0 };
		manager.setZoomLevels(zoomLevels); // ��ӷŴ����
		// ���÷ǰٷֱ�����
		ArrayList<String> zoomContributions = new ArrayList<String>();
		zoomContributions.add(ZoomManager.FIT_ALL);
		zoomContributions.add(ZoomManager.FIT_HEIGHT);
		zoomContributions.add(ZoomManager.FIT_WIDTH);
		manager.setZoomLevelContributions(zoomContributions);
		// ע��Ŵ�Action
		IAction action = new ZoomInAction(manager);
		getActionRegistry().registerAction(action);
		// ע����СAction
		action = new ZoomOutAction(manager);
		getActionRegistry().registerAction(action);

		viewer.setEditPartFactory(new PartFactory());
		// �������̾��keyHander
		KeyHandler keyHandler = new KeyHandler();

		// ��DEL��ʱִ��ɾ��Action
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(GEFActionConstants.DELETE));
		// ��F2��ʱִ��ֱ�ӱ༭Action
		keyHandler.put(KeyStroke.getPressed(SWT.F2, 0), getActionRegistry()
				.getAction(GEFActionConstants.DIRECT_EDIT));
		getGraphicalViewer().setKeyHandler(keyHandler);
	}

	@Override
	protected void initializeGraphicalViewer() {
		// set the contents of this editor
		contentsModel = new ContentsModel();
		HelloModel child1 = new HelloModel();
		child1.setConstraint(new Rectangle(0, 0, -1, -1));
		contentsModel.addChild(child1);

		HelloModel child2 = new HelloModel();
		child2.setConstraint(new Rectangle(30, 30, -1, -1));
		contentsModel.addChild(child2);
		HelloModel child3 = new HelloModel();
		child3.setConstraint(new Rectangle(10, 80, 80, 50));
		contentsModel.addChild(child3);
		viewer.setContents(contentsModel);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getCommandStack().markSaveLocation();
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		// 1.0 ����һ��palette��route
		PaletteRoot root = new PaletteRoot();
		// 2.0 ����һ�����������ڷ�ֹ����Tools
		PaletteGroup toolGroup = new PaletteGroup("����");

		// 3.0 ����һ��GEF�ṩ��"selection"���� ������ŵ�toolGroup��
		ToolEntry tool = new SelectionToolEntry();
		toolGroup.add(tool);

		// 3.1 ��(ѡ��)����Ϊȱʡ��ѡ��Ĺ���
		root.setDefaultEntry(tool);

		// 4.0 ����һ��GEF�ṩ�� "Marquee��ѡ"���߲�����ŵ�toolGroup��
		tool = new MarqueeToolEntry();
		toolGroup.add(tool);

		// 5.0 ����һ��Drawer(����)���û�ͼ����,�ó�������Ϊ"��ͼ"
		PaletteDrawer drawer = new PaletteDrawer("��ͼ");

		// ָ��"����HelloModelģ��"��������Ӧ��ͼ��
		ImageDescriptor descriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(Application.PLUGIN_ID,
						"icons//alt_window16.gif");

		// 6.0 ����"����HelloModelģ��"����
		CreationToolEntry creationToolEntry = new CreationToolEntry(
				"����HelloModel", "����HelloModelģ��", new SimpleFactory(
						HelloModel.class), descriptor, descriptor);
		drawer.add(creationToolEntry);

		// ����
		PaletteDrawer connectionDrawer = new PaletteDrawer("����");
		ImageDescriptor newConnectionDescriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(Application.PLUGIN_ID,
						IImageKeys.jiantou);
		ConnectionCreationToolEntry connCreationEntry = new ConnectionCreationToolEntry(
				"������", "����������", new SimpleFactory(LineConnectionModel.class),
				newConnectionDescriptor, newConnectionDescriptor);
		connectionDrawer.add(connCreationEntry);

		// ��ͷ����
		PaletteDrawer ArrowConnectionDrawer = new PaletteDrawer("��ͷ����");
		ImageDescriptor newArrowConnectionDescriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(Application.PLUGIN_ID,
						IImageKeys.model);
		ConnectionCreationToolEntry arrowConnCreationEntry = new ConnectionCreationToolEntry(
				"��ͷ����", "������ͷ����",
				new SimpleFactory(ArrowConnectionModel.class),
				newArrowConnectionDescriptor, newArrowConnectionDescriptor);
		ArrowConnectionDrawer.add(arrowConnCreationEntry);

		// 7.0 ��󽫴��������鹤�߼ӵ�root��
		root.add(toolGroup);
		root.add(drawer);
		root.add(connectionDrawer);
		root.add(ArrowConnectionDrawer);
		return root;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();

		// ������ע��һ��DirectEditAction
		IAction action = new DirectEditAction((IWorkbenchPart) this);
		registry.registerAction(action);

		// ��һ��action��Ҫ��ѡ��������ʱ,��Ҫע����ID ??
		getSelectionActions().add(action.getId());
		// ˮƽ�������
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		// ��ֱ�������
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
		if (type == ZoomManager.class) {
			return ((ScalableRootEditPart) getGraphicalViewer()
					.getRootEditPart()).getZoomManager();
		}// �����IContentOutlinePage ���ͣ��򷵻ظ�ContentOutlinePage
		if (type == IContentOutlinePage.class) {
			return new CustomContentOutlinePage();
		}
		return super.getAdapter(type);
	}

	@Override
	public boolean isDirty() {
		return getCommandStack().isDirty();
	}

	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	/****************************** inner class *****************************/
	class CustomContentOutlinePage extends ContentOutlinePage {
		// ʹ��SashForm��Outline��ͼ��Ϊ������,��ʾ��ٺ���ʾӥ��
		private SashForm sash;

		// ʵ��ӥ�۵�ͼ��
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;

		public CustomContentOutlinePage() {
			super(new TreeViewer());
		}

		@SuppressWarnings("deprecation")
		@Override
		public void init(IPageSite pageSite) {
			super.init(pageSite);
			// ���ע���graphical editor��Action
			ActionRegistry registry = getActionRegistry();
			// ʹ��ЩAction�ڴ����ͼ��Ҳ��Ч
			IActionBars bars = pageSite.getActionBars();
			String id = IWorkbenchActionConstants.UNDO;
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = IWorkbenchActionConstants.REDO;
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = IWorkbenchActionConstants.DELETE;
			bars.setGlobalActionHandler(id, registry.getAction(id));
			bars.updateActionBars();
		}

		@Override
		public void createControl(Composite parent) {
			// ����SashForm
			sash = new SashForm(parent, SWT.VERTICAL);
			// ���ӷָ���
			getViewer().createControl(sash);
			// ����Edit Domain
			getViewer().setEditDomain(getEditDomain());
			// ����EditPartFactory
			getViewer().setEditPartFactory(new TreeEditPartFactory());
			// ����ͼ��Ӧ����ContentsModel
			getViewer().setContents(contentsModel);
			getSelectionSynchronizer().addViewer(getViewer());
			Canvas canvas = new Canvas(sash, SWT.BORDER);
			// ʹ��LightweightSystem ����Сͼ��
			LightweightSystem lws = new LightweightSystem(canvas);
			// ���RootEditPart,�����ͼ��
			ScalableRootEditPart rootEditPart = (ScalableRootEditPart) getGraphicalViewer()
					.getRootEditPart();
			thumbnail = new ScrollableThumbnail(
					(Viewport) rootEditPart.getFigure());
			thumbnail.setSource(rootEditPart
					.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			disposeListener = new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			// ��Graphical Viewer ɾ��ʱ,ɾ��thumbnailͼ��
			getGraphicalViewer().getControl().addDisposeListener(
					disposeListener);
		}

		@Override
		public Control getControl() {
			// TODO Auto-generated method stub
			return sash;
		}

		@Override
		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());

			Control control = getGraphicalViewer().getControl();
			if (control != null
					&& !control.isDisposed())
			{
				control.removeDisposeListener(disposeListener);
				
			}
			super.dispose();
		}
	}
}
