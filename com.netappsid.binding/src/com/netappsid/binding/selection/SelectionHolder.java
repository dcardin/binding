package com.netappsid.binding.selection;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.inject.Inject;
import com.netappsid.binding.beans.Bean;
import com.netappsid.binding.beans.support.ChangeSupportFactory;

public class SelectionHolder extends Bean implements SelectionModel
{
	private SortedSet<Integer> indexes;
	
	@Inject
	public SelectionHolder(ChangeSupportFactory changeSupportFactory)
	{
		super(changeSupportFactory);
	}
	
	@Override
	public void addSelectionChangeListener(PropertyChangeListener listener)
	{
		addPropertyChangeListener(PROPERTYNAME_SELECTION, listener);
	}
	
	@Override
	public void removeSelectionChangeListener(PropertyChangeListener listener)
	{
		removePropertyChangeListener(PROPERTYNAME_SELECTION, listener);
	}
	
	@Override
	public SortedSet<Integer> getSelection()
	{
		return indexes != null ? new TreeSet<Integer>(indexes) : new TreeSet<Integer>();
	}
	
	@Override
	public boolean hasSelection()
	{
		return indexes != null;
	}
	
	@Override
	public void setSelection()
	{
		SortedSet<Integer> oldValue = indexes;
		
		indexes = null;
		fireSelectionChange(oldValue);
	}
	
	@Override
	public void setSelection(Integer index)
	{
		SortedSet<Integer> oldValue = indexes;
		
		indexes = new TreeSet<Integer>();
		indexes.add(index);
		fireSelectionChange(oldValue, index);
	}
	
	@Override
	public void setSelection(SortedSet<Integer> indexes)
	{
		SortedSet<Integer> oldValue = this.indexes;
		
		this.indexes = new TreeSet<Integer>(indexes);
		fireSelectionChange(oldValue, indexes);
	}
	
	@Override
	public void setSelectionInterval(Integer index0, Integer index1)
	{
		SortedSet<Integer> indexes = new TreeSet<Integer>();
		
		for (Integer index = index0; index <= index1; index++)
		{
			indexes.add(index);
		}
		
		setSelection(indexes);
	}
	
	@Override
	public List<Object> getSelectedInList(List<Object> list)
	{
		List<Object> selectedObjects = new ArrayList<Object>();
		
		if (list != null)
		{
			for (Integer index : getSelection())
			{
				selectedObjects.add(list.get(index));
			}
		}
		
		return selectedObjects;
	}
	
	private void fireSelectionChange(SortedSet<Integer> oldSelection)
	{
		firePropertyChange(PROPERTYNAME_SELECTION, oldSelection, null);
	}
	
	private void fireSelectionChange(SortedSet<Integer> oldSelection, Integer newSelection)
	{
		SortedSet<Integer> newSelectionCollection = new TreeSet<Integer>();
		
		newSelectionCollection.add(newSelection);
		firePropertyChange(PROPERTYNAME_SELECTION, oldSelection, newSelectionCollection);
	}
	
	private void fireSelectionChange(SortedSet<Integer> oldSelection, SortedSet<Integer> newSelection)
	{
		firePropertyChange(PROPERTYNAME_SELECTION, oldSelection, newSelection);
	}

	public static final String PROPERTYNAME_SELECTION = "selection";
}
