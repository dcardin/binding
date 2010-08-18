package com.netappsid.test.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.netappsid.binding.beans.StandardBean;

public class TestBean extends StandardBean
{
	private final String identifier;
	private String property1;
	private String property2;
	private TestBeanDetail bean1;
	private TestBeanDetail bean2;
	private List<TestBeanDetail> beansList = new ArrayList<TestBeanDetail>();
	private Set<TestBeanDetail> beansSet = new HashSet<TestBeanDetail>();
	private Map<String, TestBeanDetail> mappedBeans = new HashMap<String, TestBeanDetail>();

	public TestBean(String identifier)
	{
		this.identifier = identifier;
	}
	
	public TestBean(String identifier, TestBeanDetail... beans)
	{
		this(identifier);
		
		for (TestBeanDetail bean : beans)
		{
			beansList.add(bean);
		}
	}

	public String getIdentifier()
	{
		return identifier;
	}
	
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

	public TestBeanDetail getBean1()
	{
		return bean1;
	}

	public void setBean1(TestBeanDetail bean)
	{
		final TestBeanDetail oldValue = this.bean1;
		
		this.bean1 = bean;
		firePropertyChange(PROPERTYNAME_BEAN1, oldValue, bean);
	}

	public TestBeanDetail getBean2()
	{
		return bean2;
	}

	public void setBean2(TestBeanDetail bean)
	{
		final TestBeanDetail oldValue = this.bean2;
		
		this.bean2 = bean;
		firePropertyChange(PROPERTYNAME_BEAN2, oldValue, bean);
	}

	public List<TestBeanDetail> getBeansList()
	{
		return beansList;
	}

	public void setBeansList(List<TestBeanDetail> beans)
	{
		final List<TestBeanDetail> oldValue = this.beansList;
		
		this.beansList = beans;
		firePropertyChange(PROPERTYNAME_BEAN_LIST, oldValue, beans);
	}

	public Map<String, TestBeanDetail> getMappedBeans()
	{
		return mappedBeans;
	}

	public void setMappedBeans(Map<String, TestBeanDetail> mappedBeans)
	{
		final Map<String, TestBeanDetail> oldValue = this.mappedBeans;
		
		this.mappedBeans = mappedBeans;
		firePropertyChange(PROPERTYNAME_MAPPED_BEANS, oldValue, mappedBeans);
	}

	public Set<TestBeanDetail> getBeansSet()
	{
		return beansSet;
	}

	public void setBeansSet(Set<TestBeanDetail> beans)
	{
		final Set<TestBeanDetail> oldValue = this.beansSet;
		
		this.beansSet = beans;
		firePropertyChange(PROPERTYNAME_BEAN_SET, oldValue, beans);
	}
	
	public static final String PROPERTYNAME_IDENTIFIER = "identifier";
	public static final String PROPERTYNAME_PROPERTY1 = "property1";
	public static final String PROPERTYNAME_PROPERTY2 = "property2";
	public static final String PROPERTYNAME_BEAN1 = "bean1";
	public static final String PROPERTYNAME_BEAN2 = "bean2";
	public static final String PROPERTYNAME_BEAN_LIST = "beansList";
	public static final String PROPERTYNAME_BEAN_SET = "beansSet";
	public static final String PROPERTYNAME_MAPPED_BEANS = "mappedBeans";
}
