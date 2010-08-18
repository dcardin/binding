package com.netappsid.binding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.netappsid.binding.beans.exception.PropertyUnboundException;
import com.netappsid.binding.value.AbstractValueModel;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;
import com.netappsid.test.beans.TestBean;

public class BeanAdapterTest
{
	@Test
	public void instantiation_NullBean()
	{
		new BeanAdapter((Object) null);
	}
	
	@Test
	public void instantiation_NullValueModel()
	{
		new BeanAdapter((ValueModel) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void instantiation_RefuseDisabledIdentityCheckValueModel()
	{
		final ValueModel valueModel = new ValueHolder(null, false);
		
		new BeanAdapter(valueModel);
	}
	
	@Test(expected = PropertyUnboundException.class)
	public void instantiation_RefusesBeanThatDoesntSupportJavaBeanSpecs()
	{
		new BeanAdapter("TEST");
	}
	
	@Test
	public void getBeanChannel_InstantiatedWithBeanReturnsBindedValueModelForBean()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(bean);
		
		Assert.assertNotNull(adapter.getBeanChannel());
		Assert.assertEquals(bean, adapter.getBeanChannel().getValue());
	}
	
	@Test
	public void getBeanChannel_InstantiatedWithValueModelReturnsSameValueModel()
	{
		final ValueHolder valueModel = new ValueHolder(null, true);
		final BeanAdapter adapter = new BeanAdapter(valueModel);
		
		Assert.assertEquals(valueModel, adapter.getBeanChannel());
	}
	
	@Test
	public void getBean()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(bean);
		
		Assert.assertEquals(bean, adapter.getBean());
	}
	
	@Test
	public void setBean()
	{
		final BeanAdapter adapter = new BeanAdapter();
		final TestBean bean = new TestBean("1");
		
		adapter.setBean(bean);
		
		Assert.assertEquals(bean, adapter.getBean());
	}
	
	@Test(expected = PropertyUnboundException.class)
	public void setBean_RefusesBeanThatDoesntSupportJavaBeanSpecs()
	{
		new BeanAdapter().setBean("TEST");
	}
	
	@Test
	public void getValue()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(bean);
		
		bean.setProperty1("TEST_GET_VALUE");
		
		Assert.assertEquals("TEST_GET_VALUE", adapter.getValue(TestBean.PROPERTYNAME_PROPERTY1));
	}
	
	@Test
	public void getValue_NullBean_ReturnsNull()
	{
		Assert.assertNull(new BeanAdapter().getValue(TestBean.PROPERTYNAME_PROPERTY1));
	}
	
	@Test
	public void getValue_InvalidProperty_ReturnsNull()
	{
		Assert.assertNull(new BeanAdapter(new TestBean("1")).getValue("invalid"));
	}
	
	@Test
	public void setValue()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(bean);
		
		adapter.setValue(TestBean.PROPERTYNAME_PROPERTY1, "TEST_SET_VALUE");
		
		Assert.assertEquals("TEST_SET_VALUE", bean.getProperty1());
	}
	
	@Test
	public void getValueModel()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(bean);
		final BeanPropertyValueModel propertyAdapter = adapter.getValueModel(TestBean.PROPERTYNAME_PROPERTY1);
		
		bean.setProperty1("TEST_GET_VALUE_MODEL");
		
		Assert.assertNotNull(propertyAdapter);
		Assert.assertEquals(TestBean.PROPERTYNAME_PROPERTY1, propertyAdapter.getPropertyName());
		Assert.assertEquals("TEST_GET_VALUE_MODEL", propertyAdapter.getValue());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getValueModel_RefuseNullPropertyName()
	{
		new BeanAdapter().getValueModel(null);
	}
	
	@Test
	public void addBeanPropertyChangeListener()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter();
		
		adapter.addBeanPropertyChangeListener(listener);
		Assert.assertEquals(listener, adapter.getBeanPropertyChangeListeners()[0]);
	}
	
	@Test
	public void addBeanPropertyChangeListener_SpecificProperty()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter();
		
		adapter.addBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listener);
		Assert.assertEquals(listener, adapter.getBeanPropertyChangeListeners(TestBean.PROPERTYNAME_PROPERTY1)[0]);
		Assert.assertEquals(0, adapter.getBeanPropertyChangeListeners().length);
	}
	
	@Test
	public void removeBeanPropertyChangeListener()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter();
		
		adapter.addBeanPropertyChangeListener(listener);
		adapter.removeBeanPropertyChangeListener(listener);
		Assert.assertEquals(0, adapter.getBeanPropertyChangeListeners().length);
	}
	
	@Test
	public void removeBeanPropertyChangeListener_SpecificProperty()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter();
		
		adapter.addBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listener);
		adapter.removeBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listener);
		Assert.assertEquals(0, adapter.getBeanPropertyChangeListeners(TestBean.PROPERTYNAME_PROPERTY1).length);
	}
	
	@Test
	public void release()
	{
		final TestBean bean = new TestBean("1");
		final int listenerCount = bean.getPropertyChangeListeners().length;
		final BeanAdapter adapter = new BeanAdapter(bean);
		
		adapter.release();
		Assert.assertEquals(listenerCount, bean.getPropertyChangeListeners().length);
	}
	
	@Test
	public void setBean_ForwardsBeanListeners()
	{
		final BeanAdapter adapter = new BeanAdapter(new TestBean("1"));
		final PropertyChangeListenerSpy listenerSpy = new PropertyChangeListenerSpy();
		
		adapter.getValueModel(TestBean.PROPERTYNAME_PROPERTY1);
		adapter.addBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listenerSpy);
		
		final TestBean bean = new TestBean("2");
		
		adapter.setBean(bean);
		Assert.assertEquals(listenerSpy, bean.getPropertyChangeListeners(TestBean.PROPERTYNAME_PROPERTY1)[0]);
	}
	
	@Test
	public void setBean_AdaptedValuesListenersNotified()
	{
		final BeanAdapter adapter = new BeanAdapter(new TestBean("1"));
		final PropertyChangeListenerSpy listenerSpy = new PropertyChangeListenerSpy();
		final ValueModel valueModel = adapter.getValueModel(TestBean.PROPERTYNAME_PROPERTY1);
		
		valueModel.addValueChangeListener(listenerSpy);
		
		final TestBean bean = new TestBean("2");
		
		bean.setProperty1("TEST_FORWARD");
		adapter.setBean(bean);
		
		Assert.assertEquals(1, listenerSpy.getFiredEvents().size());
		Assert.assertEquals(AbstractValueModel.PROPERTYNAME_VALUE, listenerSpy.getFiredEvents().get(0).getPropertyName());
		Assert.assertEquals("TEST_FORWARD", listenerSpy.getFiredEvents().get(0).getNewValue());
	}
	
	@Test
	public void setBean_AdaptedValuesListenersNotifiedWhenGlobalPropertyChangeFired()
	{
		final NonSpecificFireBean bean = new NonSpecificFireBean();
		final BeanAdapter adapter = new BeanAdapter(bean);
		final PropertyChangeListenerSpy listenerSpy = new PropertyChangeListenerSpy();
		final ValueModel valueModel = adapter.getValueModel("property");
		
		valueModel.addValueChangeListener(listenerSpy);
		
		bean.setProperty("TEST_FORWARD");
		
		Assert.assertEquals(1, listenerSpy.getFiredEvents().size());
		Assert.assertEquals(AbstractValueModel.PROPERTYNAME_VALUE, listenerSpy.getFiredEvents().get(0).getPropertyName());
		Assert.assertEquals("TEST_FORWARD", listenerSpy.getFiredEvents().get(0).getNewValue());
	}
	
	public static class PropertyChangeListenerSpy implements PropertyChangeListener
	{
		private final List<PropertyChangeEvent> firedEvents = new ArrayList<PropertyChangeEvent>(); 
		
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			firedEvents.add(evt);
		}
		
		public List<PropertyChangeEvent> getFiredEvents()
		{
			return firedEvents;
		}
	}
	
	public static class NonSpecificFireBean extends StandardBean
	{
		private String property;
		
		public String getProperty()
		{
			return property;
		}
		
		public void setProperty(String property)
		{
			this.property = property;
			firePropertyChange(null, null, property);
		}
	}
}
