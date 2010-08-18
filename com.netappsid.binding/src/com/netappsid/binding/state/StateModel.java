package com.netappsid.binding.state;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.netappsid.binding.beans.Bean;
import com.netappsid.binding.beans.support.StandardChangeSupportFactory;

public class StateModel extends Bean
{
	private final List<StateModel> parents;
	private final List<StateModel> children;
	
	private State state;
	
	public StateModel()
	{
		super(new StandardChangeSupportFactory());
		
		this.parents = new ArrayList<StateModel>();
		this.children = new ArrayList<StateModel>();
		
		this.state = State.CLEAN;
	}
	
	public final State getState()
	{
		return state;
	}
	
	public final void setState(State state)
	{
		final State oldValue = this.state;
		
		this.state = state;
		firePropertyChange(PROPERTYNAME_STATE, oldValue, state);
		propagateState(state);
	}
	
	public final void resetState()
	{
		setState(State.CLEAN);
	}
	
	public final void addStateChangeListener(PropertyChangeListener listener)
	{
		addPropertyChangeListener(PROPERTYNAME_STATE, listener);
	}
	
	public final void removeStateChangeListener(PropertyChangeListener listener)
	{
		removePropertyChangeListener(PROPERTYNAME_STATE, listener);
	}
	
	public final void link(StateModel stateModel)
	{		
		children.add(stateModel);
		stateModel.parents.add(this);
		
		if (stateModel.getState().isBubbling())
		{
			setState(stateModel.getState());
		}
	}
	
	public final void unlink(StateModel stateModel)
	{
		children.remove(stateModel);
		stateModel.parents.remove(stateModel);
	}
	
	private void propagateState(State state)
	{
		if (state.isBubbling())
		{
			propagateStateToNodes(state, parents);
		}
		else
		{
			propagateStateToNodes(state, children);
		}
	}
	
	private void propagateStateToNodes(State state, List<StateModel> nodes)
	{
		for (StateModel stateModel : nodes)
		{
			stateModel.setState(state);
		}
	}
		
	public static final String PROPERTYNAME_STATE = "state";
}
