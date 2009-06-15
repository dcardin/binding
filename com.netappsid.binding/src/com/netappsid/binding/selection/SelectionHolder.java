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
		
		indexes = null;
		fireSelectionChange(oldValue);
	}
	
	public void setSelection(Integer index)
	{
		SortedSet<Integer> oldValue = indexes;
		
		indexes = new TreeSet<Integer>();
		indexes.add(index);
		fireSelectionChange(oldValue, index);
	}
	
	public void setSelection(SortedSet<Integer> indexes)
	{
		SortedSet<Integer> oldValue = this.indexes;
		
		this.indexes = new TreeSet<Integer>(indexes);
		fireSelectionChange(oldValue, indexes);
	}
	
	public void setSelectionInterval(Integer index0, Integer index1)
	{
		SortedSet<Integer> indexes = new TreeSet<Integer>();
		
		for (Integer index = index0; index <= index1; index++)
		{
			indexes.add(index);
		}
		
		setSelection(indexes);
	}
}
