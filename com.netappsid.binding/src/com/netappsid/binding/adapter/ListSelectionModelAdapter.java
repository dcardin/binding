package com.netappsid.binding.adapter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.netappsid.binding.selection.SelectionModel;
import com.netappsid.validate.Validate;

public class ListSelectionModelAdapter implements ListSelectionModel
{
	private final ListSelectionListener listSelectionChangeHandler = new ListSelectionChangeHandler();
	private final PropertyChangeListener selectionChangeHandler = new SelectionChangeHandler();
	private ListSelectionModel adapted;
	private SelectionModel selectionChannel;
	
	public ListSelectionModelAdapter(SelectionModel selectionChannel)
	{
		this(selectionChannel, new DefaultListSelectionModel());
	}
	
	public ListSelectionModelAdapter(SelectionModel selectionChannel, ListSelectionModel adapted)
	{
		this.selectionChannel = Validate.notNull(selectionChannel, "Selection channel must not be null.");
		this.adapted = Validate.notNull(adapted, "ListSelectionModel cannot be null.");
		
		selectionChannel.addSelectionChangeListener(selectionChangeHandler);
		adapted.addListSelectionListener(listSelectionChangeHandler);
	}
	
	public static void connect(SelectionModel selectionChannel, ListSelectionModel adapted)
	{
		new ListSelectionModelAdapter(selectionChannel, adapted);
	}
	
	public void addListSelectionListener(ListSelectionListener x)
	{
		getListSelectionModel().addListSelectionListener(x);
	}
	
	public void addSelectionInterval(int index0, int index1)
	{
		getListSelectionModel().addSelectionInterval(index0, index1);
	}

	public void clearSelection()
	{
		getListSelectionModel().clearSelection();
	}

	public int getAnchorSelectionIndex()
	{
		return getListSelectionModel().getAnchorSelectionIndex();
	}

	public int getLeadSelectionIndex()
	{
		return getListSelectionModel().getLeadSelectionIndex();
	}

	public int getMaxSelectionIndex()
	{
		return getListSelectionModel().getMaxSelectionIndex();
	}

	public int getMinSelectionIndex()
	{
		return getListSelectionModel().getMinSelectionIndex();
	}

	public int getSelectionMode()
	{
		return getListSelectionModel().getSelectionMode();
	}

	public boolean getValueIsAdjusting()
	{
		return getListSelectionModel().getValueIsAdjusting();
	}

	public void insertIndexInterval(int index, int length, boolean before)
	{
		getListSelectionModel().insertIndexInterval(index, length, before);
	}

	public boolean isSelectedIndex(int index)
	{
		return getListSelectionModel().isSelectedIndex(index);
	}

	public boolean isSelectionEmpty()
	{
		return getListSelectionModel().isSelectionEmpty();
	}

	public void removeIndexInterval(int index0, int index1)
	{
		getListSelectionModel().removeIndexInterval(index0, index1);
	}

	public void removeListSelectionListener(ListSelectionListener x)
	{
		getListSelectionModel().removeListSelectionListener(x);
	}

	public void removeSelectionInterval(int index0, int index1)
	{
		getListSelectionModel().removeSelectionInterval(index0, index1);
	}

	public void setAnchorSelectionIndex(int index)
	{
		getListSelectionModel().setAnchorSelectionIndex(index);
	}

	public void setLeadSelectionIndex(int index)
	{
		getListSelectionModel().setLeadSelectionIndex(index);
	}

	public void setSelectionInterval(int index0, int index1)
	{
		getListSelectionModel().setSelectionInterval(index0, index1);
	}

	public void setSelectionMode(int selectionMode)
	{
		getListSelectionModel().setSelectionMode(selectionMode);
	}

	public void setValueIsAdjusting(boolean valueIsAdjusting)
	{
		getListSelectionModel().setValueIsAdjusting(valueIsAdjusting);
	}

	private ListSelectionModel getListSelectionModel()
	{
		return adapted;
	}
	
	private final class ListSelectionChangeHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			adapted.removeListSelectionListener(listSelectionChangeHandler);
			selectionChannel.removeSelectionChangeListener(selectionChangeHandler);
			
			if (!adapted.isSelectionEmpty())
			{
				SortedSet<Integer> indexes = new TreeSet<Integer>();
				
				for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++)
				{
					if (adapted.isSelectedIndex(i))
					{
						indexes.add(i);
					}
				}
				
				selectionChannel.setSelection(indexes);
			}
			else
			{
				selectionChannel.setSelection();
			}
			
			adapted.addListSelectionListener(listSelectionChangeHandler);
			selectionChannel.addSelectionChangeListener(selectionChangeHandler);
		}
	}
	
	private final class SelectionChangeHandler implements PropertyChangeListener
	{
		@SuppressWarnings("unchecked")
		public void propertyChange(PropertyChangeEvent evt)
		{
			adapted.removeListSelectionListener(listSelectionChangeHandler);
			selectionChannel.removeSelectionChangeListener(selectionChangeHandler);
			
			SortedSet<Integer> indexes = (SortedSet<Integer>) evt.getNewValue();
			
			if (indexes == null)
			{
				adapted.clearSelection();
			}
			else
			{
				adapted.setValueIsAdjusting(true);
				adapted.clearSelection();
				
				for (Integer index : indexes)
				{
					adapted.addSelectionInterval(index, index);
				}
				
				adapted.setValueIsAdjusting(false);
			}
			
			adapted.addListSelectionListener(listSelectionChangeHandler);
			selectionChannel.addSelectionChangeListener(selectionChangeHandler);
		}
	}
}
