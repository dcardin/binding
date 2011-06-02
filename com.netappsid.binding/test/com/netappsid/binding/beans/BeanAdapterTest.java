package com.netappsid.binding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jgoodies.binding.beans.PropertyUnboundException;
import com.jgoodies.binding.value.ValueModel;
import com.netappsid.binding.beans.support.StandardChangeSupportFactory;
import com.netappsid.binding.value.AbstractValueModel;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.test.beans.TestBean;

public class BeanAdapterTest
{
	@Test
	public void testInstantiation_NullBean()
	{
		new BeanAdapter(new StandardChangeSupportFactory(), (Object) null, Object.class);
	}

	@Test
	public void testInstantiation_NullValueModel()
	{
		new BeanAdapter(new StandardChangeSupportFactory(), (ValueModel) null, Object.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInstantiation_RefuseDisabledIdentityCheckValueModel()
	{
		final ValueModel valueModel = new ValueHolder(new StandardChangeSupportFactory(), null, false);

		new BeanAdapter(new StandardChangeSupportFactory(), valueModel, Object.class);
	}

	@Test(expected = PropertyUnboundException.class)
	public void testInstantiation_RefusesBeanThatDoesntSupportJavaBeanSpecs()
	{
		new BeanAdapter(new StandardChangeSupportFactory(), "TEST", String.class);
	}

	@Test
	public void testGetBeanChannel_InstantiatedWithBeanReturnsBindedValueModelForBean()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);

		Assert.assertNotNull(adapter.getBeanChannel());
		Assert.assertEquals(bean, adapter.getBeanChannel().getValue());
	}

	@Test
	public void testGetBeanChannel_InstantiatedWithValueModelReturnsSameValueModel()
	{
		final ValueHolder valueModel = new ValueHolder(new StandardChangeSupportFactory(), null, true);
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), valueModel, TestBean.class);

		Assert.assertEquals(valueModel, adapter.getBeanChannel());
	}

	@Test
	public void testGetBean()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);

		Assert.assertEquals(bean, adapter.getBean());
	}

	@Test
	public void testSetBean()
	{
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class);
		final TestBean bean = new TestBean("1");

		adapter.setBean(bean);

		Assert.assertEquals(bean, adapter.getBean());
	}

	@Test(expected = PropertyUnboundException.class)
	public void testSetBean_RefusesBeanThatDoesntSupportJavaBeanSpecs()
	{
		new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class).setBean("TEST");
	}

	@Test
	public void testGetValue()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);

		bean.setProperty1("TEST_GET_VALUE");

		Assert.assertEquals("TEST_GET_VALUE", adapter.getValue(TestBean.PROPERTYNAME_PROPERTY1));
	}

	@Test
	public void testGetValue_NullBean_ReturnsNull()
	{
		Assert.assertNull(new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class).getValue(TestBean.PROPERTYNAME_PROPERTY1));
	}

	@Test
	public void testGetValue_InvalidProperty_ReturnsNull()
	{
		Assert.assertNull(new BeanAdapter(new StandardChangeSupportFactory(), new TestBean("1"), TestBean.class).getValue("invalid"));
	}

	@Test
	public void testSetValue()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);

		adapter.setValue(TestBean.PROPERTYNAME_PROPERTY1, "TEST_SET_VALUE");

		Assert.assertEquals("TEST_SET_VALUE", bean.getProperty1());
	}

	@Test
	public void testGetValueModel()
	{
		final TestBean bean = new TestBean("1");
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);
		final SimplePropertyAdapter propertyAdapter = adapter.getValueModel(TestBean.PROPERTYNAME_PROPERTY1);

		bean.setProperty1("TEST_GET_VALUE_MODEL");

		Assert.assertNotNull(propertyAdapter);
		Assert.assertEquals(TestBean.PROPERTYNAME_PROPERTY1, propertyAdapter.getPropertyName());
		Assert.assertEquals("TEST_GET_VALUE_MODEL", propertyAdapter.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValueModel_RefuseNullPropertyName()
	{
		new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class).getValueModel(null);
	}

	@Test
	public void testAddBeanPropertyChangeListener()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class);

		adapter.addBeanPropertyChangeListener(listener);
		Assert.assertEquals(listener, adapter.getBeanPropertyChangeListeners()[0]);
	}

	@Test
	public void testAddBeanPropertyChangeListener_SpecificProperty()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class);

		adapter.addBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listener);
		Assert.assertEquals(listener, adapter.getBeanPropertyChangeListeners(TestBean.PROPERTYNAME_PROPERTY1)[0]);
		Assert.assertEquals(0, adapter.getBeanPropertyChangeListeners().length);
	}

	@Test
	public void testRemoveBeanPropertyChangeListener()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class);

		adapter.addBeanPropertyChangeListener(listener);
		adapter.removeBeanPropertyChangeListener(listener);
		Assert.assertEquals(0, adapter.getBeanPropertyChangeListeners().length);
	}

	@Test
	public void testRemoveBeanPropertyChangeListener_SpecificProperty()
	{
		final PropertyChangeListener listener = new PropertyChangeListenerSpy();
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), TestBean.class);

		adapter.addBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listener);
		adapter.removeBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listener);
		Assert.assertEquals(0, adapter.getBeanPropertyChangeListeners(TestBean.PROPERTYNAME_PROPERTY1).length);
	}

	@Test
	public void testRelease()
	{
		final TestBean bean = new TestBean("1");
		final int listenerCount = bean.getPropertyChangeListeners().length;
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);

		adapter.release();
		Assert.assertEquals(listenerCount, bean.getPropertyChangeListeners().length);
	}

	@Test
	public void testSetBean_ForwardsBeanListeners()
	{
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), new TestBean("1"), TestBean.class);
		final PropertyChangeListenerSpy listenerSpy = new PropertyChangeListenerSpy();

		adapter.getValueModel(TestBean.PROPERTYNAME_PROPERTY1);
		adapter.addBeanPropertyChangeListener(TestBean.PROPERTYNAME_PROPERTY1, listenerSpy);

		final TestBean bean = new TestBean("2");

		adapter.setBean(bean);
		Assert.assertEquals(listenerSpy, bean.getPropertyChangeListeners(TestBean.PROPERTYNAME_PROPERTY1)[0]);
	}

	@Test
	public void testSetBean_AdaptedValuesListenersNotified()
	{
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), new TestBean("1"), TestBean.class);
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
	public void testSetBean_AdaptedValuesListenersNotifiedWhenGlobalPropertyChangeFired()
	{
		final NonSpecificFireBean bean = new NonSpecificFireBean();
		final BeanAdapter adapter = new BeanAdapter(new StandardChangeSupportFactory(), bean, TestBean.class);
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
