package gef.tutorial.step.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import gef.tutorial.step.command.DirectEditCommand;

public class CustomDirectEditPolicy extends DirectEditPolicy {

	@Override
	//����ѡ��cell editor,�޸��ı�,cell editorʧȥ����֮ǰgetDirectEditCoommand����
	protected Command getDirectEditCommand(DirectEditRequest request) {
		DirectEditCommand command = new DirectEditCommand();
		command.setModel(getHost().getModel());
		command.setText((String) request.getCellEditor().getValue());
		return command;
	}

	@Override
	//showCurrentEditValue����������ʾFigure�еĵ�ǰֱ�ӱ༭ֵ
	//��ȻCellEditor���ܸ�ס��ͼ�ζԸ�ֵ����ʾ,���Ǹ���ͼ�λ�ʹ�����ųߴ���Ӧ��ֵ
	protected void showCurrentEditValue(DirectEditRequest request) {
		// TODO Auto-generated method stub

	}

}
