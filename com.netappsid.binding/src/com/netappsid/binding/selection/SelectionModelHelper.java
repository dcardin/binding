package com.netappsid.binding.selection;

import java.util.ArrayList;
import java.util.List;

public class SelectionModelHelper
{
	private SelectionModelHelper()
	{
	// Hidden constructor
	}
	
	public static List<Object> getSelectedInList(List<Object> list, SelectionModel selectionModel)
	{
		List<Object> selectedObjects = new ArrayList<Object>();
		
		if (list != null && selectionModel != null)
		{
			for (Integer index : selectionModel.getSelection())
			{
				selectedObjects.add(list.get(index));
			}
		}
		
		return selectedObjects;
	}
}
