package com.netappsid.binding;

import org.junit.Assert;
import org.junit.Test;

import com.netappsid.binding.beans.StandardBean;
import com.netappsid.binding.value.ValueModel;

public class DefaultPresentationModelTest
{
	@Test
	public void testStabilityWhenChangingPresentedInstanceType()
	{
		final PresentationModel rootModel = new DefaultPresentationModel(TestModel.class);
		final ValueModel property1Channel = rootModel.getValueModel("property1");
		final ValueModel property2Channel = rootModel.getValueModel("property2");
		
		rootModel.setBean(new TestSubModel1());
		
		Assert.assertEquals("PROPERTY1", property1Channel.getValue());
		Assert.assertEquals(null, property2Channel.getValue());
		
		rootModel.setBean(new TestSubModel2());
		
		Assert.assertEquals(null, property1Channel.getValue());
		Assert.assertEquals("PROPERTY2", property2Channel.getValue());
	}

	public static class TestModel extends StandardBean
	{

	}

	public static class TestSubModel1 extends TestModel
	{
		private String property1 = "PROPERTY1";

		public String getProperty1()
		{
			return property1;
		}

		public void setProperty1(String property1)
		{
			final String oldValue = this.property1;

			this.property1 = property1;
			firePropertyChange(PROPERTYNAME_PROPERTY1, oldValue, property1);
		}

		public static final String PROPERTYNAME_PROPERTY1 = "property1";
	}

	public static class TestSubModel2 extends TestModel
	{
		private String property2 = "PROPERTY2";

		public String getProperty2()
		{
			return property2;
		}

		public void setProperty2(String property2)
		{
			final String oldValue = this.property2;

			this.property2 = property2;
			firePropertyChange(PROPERTYNAME_PROPERTY2, oldValue, property2);
		}

		public static final String PROPERTYNAME_PROPERTY2 = "property2";
	}
}
