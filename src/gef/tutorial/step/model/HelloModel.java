package gef.tutorial.step.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class HelloModel extends AbstractModel {
	private String text = "Hello world";
	private Rectangle constraint;
	public static final String P_CONSTRAINT = "_constraint";
	// ����ַ�����ID �����ı�ͼ�ε��ı�ʱ��֪ͨ��Editpart
	public static final String P_TEXT = "_text";
	 // connection
    public static final String P_SOURCE_CONNECTION = "_source_connection";
    public static final String P_TARGET_CONNECTION = "_target_connection";
    // ������������Ϊ��ʼ�������Ϊ�յ����
    private List<Object> sourceConnection = new ArrayList<Object>();
    private List<Object> targetConnection = new ArrayList<Object>();

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		firePropertyChange(P_TEXT, null, text);
	}

	public Rectangle getConstraint() {
		return constraint;
	}

	public void setConstraint(Rectangle constraint) {
		this.constraint = constraint;
		firePropertyChange(P_CONSTRAINT, null, constraint);
	}

	public void addSourceConnection(Object conn)
	{
	    sourceConnection.add(conn);
	    firePropertyChange(P_SOURCE_CONNECTION, null, null);
	
	}

	public void removeSourceConnection(Object conn)
	{
	    sourceConnection.remove(conn);
	    firePropertyChange(P_SOURCE_CONNECTION, null, null);
	}

	public void addTargetConnection(Object conn)
	{
	    targetConnection.add(conn);
	    firePropertyChange(P_TARGET_CONNECTION, null, null);
	}

	public void removeTargetConnection(Object conn)
	{
	    targetConnection.remove(conn);
	    firePropertyChange(P_TARGET_CONNECTION, null, null);
	}

	public List<Object> getModelSourceConnection()
	{
	    return sourceConnection;
	}

	public List<Object> getModelTargetConnection()
	{
	    return targetConnection;
	}

	// ��ʵ������ͼ����tableview����ʾ����,��һ������������,��2��������ֵ.
	// IPropertyDescriptor[]�������˼��������������������Ƶ�.��������ֻ�ṩһ������
	// ������Ϊgreeeting
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] descriptors = new IPropertyDescriptor[] { new TextPropertyDescriptor(
				P_TEXT, "Greeting") };
		return descriptors;
	}

	// ʹ�����Ե�ID����ø�������������ͼ��ֵ
	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(P_TEXT)) {
			return text;
		}
		return null;
	}

	// �ж�������ͼ�е�����ֵ�Ƿ�ı�,���û��ָ������ֵ�򷵻�false
	@Override
	public boolean isPropertySet(Object id) {
		if (id.equals(P_TEXT)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id.equals(P_TEXT)) {
			setText((String) value);
		}
	}
}
