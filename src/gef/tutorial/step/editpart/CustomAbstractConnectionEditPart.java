package gef.tutorial.step.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;

import gef.tutorial.step.model.AbstractConnectionModel;
import gef.tutorial.step.policy.CustomBendpointEditPolicy;
import gef.tutorial.step.policy.CustomConnectionEditPolicy;
import gef.tutorial.step.policy.CustomConnectionEndpointEditPolicy;

public class CustomAbstractConnectionEditPart extends
		AbstractConnectionEditPart implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		PolylineConnection conn = new PolylineConnection();
		conn.setConnectionRouter(new BendpointConnectionRouter());
		return conn;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new CustomConnectionEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new CustomConnectionEndpointEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new CustomBendpointEditPolicy());

	}

	@Override
	public void activate() {
		super.activate();
		((AbstractConnectionModel) getModel()).addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		super.deactivate();
		((AbstractConnectionModel) getModel())
				.removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(AbstractConnectionModel.P_BEND_POINT))
			refreshBendpoints(); // ˢ�¿��Ƶ�
	}

	protected void refreshBendpoints() {
		// ���Ȼ��bending ���λ��
		List<Point> bendpoints = ((AbstractConnectionModel) getModel())
				.getBendpoints();
		// ���Ƶ���б�
		List<Point> constraint = new ArrayList<Point>();
		for (int i = 0; i < bendpoints.size(); i++) {
			// ��������ģ�͵����ݴ���һ�����Ƶ�
			constraint.add(new AbsoluteBendpoint((Point) bendpoints.get(i)));
		}
		// ����һ�����ӣ��Ѹղ����ɵĿ��Ƶ���ΪԼ��
		getConnectionFigure().setRoutingConstraint(constraint);
	}

	// ����
	protected void refreshVisuals() {
		refreshBendpoints();
	}

}