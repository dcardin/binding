package com.netappsid.binding.state;

public enum State
{
	CLEAN
	{
		public boolean isBubbling()
		{
			return false;
		}
	},
	
	DIRTY
	{
		public boolean isBubbling()
		{
			return true;
		}
	};

	/**
	 * Determines if the state will be bubbled up the hierarchy of state model.
	 * 
	 * @return true if bubbling up the hierarchy, false if propagates downwards.
	 */
	public abstract boolean isBubbling();
}
