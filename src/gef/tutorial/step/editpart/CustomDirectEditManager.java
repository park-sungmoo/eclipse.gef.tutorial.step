package gef.tutorial.step.editpart;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.widgets.Text;

import gef.tutorial.step.model.HelloModel;

public class CustomDirectEditManager extends DirectEditManager {

	private HelloModel helloModel; // ��Ҫ�޸ĸ�ģ�͵��ı�����

	@SuppressWarnings("rawtypes")
	public CustomDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator) {
		super(source, editorType, locator);
		helloModel = (HelloModel) source.getModel();
	}

	@Override
	protected void initCellEditor() {
		// ����ʾһ��cell editor֮ǰ,�ȸ�������һ��ֵ
		// �����ֵʱ���ͼ��ģ�͵��ı�
		getCellEditor().setValue(helloModel.getText());
		// ����ѡ�е�TextCellEditor��Text�ռ��е������ı�����ʾΪѡ��״̬
		Text text = (Text) getCellEditor().getControl();
		text.selectAll();

	}

}
