package com.netappsid.binding.selection;

import java.beans.PropertyChangeListener;
import java.util.SortedSet;

/**
 * 
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.1 $
 */
public interface SelectionModel
{
	void addSelectionChangeListener(PropertyChangeListener listener);
	SortedSet<Integer> getSelection();
	void removeSelectionChangeListener(PropertyChangeListener listener);
	void setSelection();
	void setSelection(Integer index);
	void setSelection(SortedSet<Integer> indexes);
}
