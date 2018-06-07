package gef.tutorial.step.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class AbstractModel implements IPropertySource
{
    //the list of listeners
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        listeners.addPropertyChangeListener(listener);
    }
    //change of a model is notified
      public void firePropertyChange(String propName,Object oldValue,Object newValue)
      {
          listeners.firePropertyChange(propName, oldValue, newValue);
      }
      //deletion of listeners
      public void removePropertyChangeListener(PropertyChangeListener listener)
      {
          listeners.removePropertyChangeListener(listener);
      }
    @Override
    public Object getEditableValue()
    {
        // TODO Auto-generated method stub
        return this;
    }
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors()
    {
        //����ڳ���ģ���з���null������쳣,������ﷵ��һ������Ϊ0������
        //���潫����IPropertyDescriptor����
        return new IPropertyDescriptor[0];
    }
    @Override
    public Object getPropertyValue(Object id)
    {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean isPropertySet(Object id)
    {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void resetPropertyValue(Object id)
    {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void setPropertyValue(Object id, Object value)
    {
        // TODO Auto-generated method stub
        
    }
}
