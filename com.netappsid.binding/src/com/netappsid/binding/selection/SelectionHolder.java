package com.netappsid.binding.selection;

import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings("serial")
public class SelectionHolder extends AbstractSelectionModel
{
	private SortedSet<Integer> indexes;
	
	public SortedSet<Integer> getSelection()
	{
		return indexes != null ? new TreeSet<Integer>(indexes) : null;
	}
	
	public void setSelection()
	{
		SortedSet<Integer> oldValue = indexes;
		
		this.indexes = null;
		fireSelectionChange(oldValue);
	}
	
	public void setSelection(Integer value)
	{
		SortedSet<Integer> oldValue = indexes;
		
		this.indexes = new TreeSet<Integer>();
		this.indexes.add(value);
		fireSelectionChange(oldValue, value);
	}
	
	public void setSelection(SortedSet<Integer> value)
	{
		SortedSet<Integer> oldValue = indexes;
		
		this.indexes = new TreeSet<Integer>(value);
		fireSelectionChange(oldValue, value);
	}
}
