package com.netappsid.binding.beans.support;

import java.beans.VetoableChangeSupport;

public interface ChangeSupportFactory
{
	IdentityPropertyChangeSupport createIdentityPropertyChangeSupport(Object sourceBean);
	
	VetoableChangeSupport createVetoableChangeSupport(Object sourceBean);
}
