package com.netappsid.binding.selection;

import java.beans.PropertyChangeListener;
import java.util.SortedSet;
import java.util.TreeSet;

import com.netappsid.binding.beans.Bean;
import com.netappsid.binding.beans.support.ChangeSupportFactory;

@SuppressWarnings("serial")
public abstract class AbstractSelectionModel extends Bean implements SelectionModel
{
	public static final String PROPERTYNAME_SELECTION = "selection";
	
	public AbstractSelectionModel(ChangeSupportFactory changeSupportFactory)
	{
		super(changeSupportFactory);
	}
	
	public final void addSelectionChangeListener(PropertyChangeListener listener)
	{
		addPropertyChangeListener(PROPERTYNAME_SELECTION, listener);
	}
	
	public final void removeSelectionChangeListener(PropertyChangeListener listener)
	{
		removePropertyChangeListener(PROPERTYNAME_SELECTION, listener);
	}
	
	protected final void fireSelectionChange(SortedSet<Integer> oldSelection)
	{
		firePropertyChange(PROPERTYNAME_SELECTION, oldSelection, null);
	}
	
	protected final void fireSelectionChange(SortedSet<Integer> oldSelection, Integer newSelection)
	{
		SortedSet<Integer> newSelectionCollection = new TreeSet<Integer>();
		
		newSelectionCollection.add(newSelection);
		firePropertyChange(PROPERTYNAME_SELECTION, oldSelection, newSelectionCollection);
	}
	
	protected final void fireSelectionChange(SortedSet<Integer> oldSelection, SortedSet<Integer> newSelection)
	{
		firePropertyChange(PROPERTYNAME_SELECTION, oldSelection, newSelection);
	}
}
