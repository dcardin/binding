package com.netappsid.binding.selection;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.SortedSet;

/**
 * @author Eric Belanger
 * @author NetAppsID Inc.
 */
public interface SelectionModel
{
	void addSelectionChangeListener(PropertyChangeListener listener);
	
	void removeSelectionChangeListener(PropertyChangeListener listener);

	SortedSet<Integer> getSelection();

	boolean hasSelection();

	void setSelection();

	void setSelection(Integer index);

	void setSelection(SortedSet<Integer> indexes);

	void setSelectionInterval(Integer index0, Integer index1);
	
	List<Object> getSelectedInList(List<Object> list);
}
