package gef.tutorial.step.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;

import gef.tutorial.step.model.AbstractConnectionModel;
import gef.tutorial.step.model.ContentsModel;
import gef.tutorial.step.model.HelloModel;

public class DeleteCommand extends Command {
	private ContentsModel contentsModel;
	private HelloModel helloModel;

	private List<Object> sourceConnections = new ArrayList<Object>();
	private List<Object> targetConnections = new ArrayList<Object>();

	@Override
	public void execute() {
		// delete connection
		sourceConnections.addAll(helloModel.getModelSourceConnection());
		targetConnections.addAll(helloModel.getModelTargetConnection());
		// ɾ����ģ�Ͷ����Ӧ��source
		for (int i = 0; i < sourceConnections.size(); i++) {
			AbstractConnectionModel model = (AbstractConnectionModel) sourceConnections
					.get(i);
			model.detachSource();
			model.detachTarget();
		}
		// ɾ����ģ�Ͷ����Ӧ��target
		for (int i = 0; i < targetConnections.size(); i++) {
			AbstractConnectionModel model = (AbstractConnectionModel) targetConnections
					.get(i);
			model.detachSource();
			model.detachTarget();
		}
		contentsModel.removeChild(helloModel);
	}

	public void setContentsModel(Object model) {
		contentsModel = (ContentsModel) model;
	}

	public void setHelloModel(Object model) {
		helloModel = (HelloModel) model;
	}

	@Override
	public void undo() {
		contentsModel.addChild(helloModel);
		
		for(int i=0;i<sourceConnections.size();i++){
		AbstractConnectionModel model=(AbstractConnectionModel)sourceConnections.get(i);
		model.attachSource();
		model.attachTarget();
		}
		for(int i=0;i<targetConnections.size();i++){
		AbstractConnectionModel model=(AbstractConnectionModel)targetConnections.get(i);
		model.attachSource();
		model.attachTarget();
		}
		//�����¼����Щ��¼���ڻָ�
		sourceConnections.clear();
		targetConnections.clear();
	}
}
