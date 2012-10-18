package ru.tomtrix.agentsocks.mathmodel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class State
{
	private final Map<String, StateItem> _list = new ConcurrentHashMap<>();
	
	public void addStateItem(String name, Class<?> T, StateItemAccess access, Object initialValue) throws Exception
	{
		if (_list.containsKey(name)) throw new IllegalArgumentException("Such name already exists");
		_list.put(name, new StateItem(T, access, initialValue));
	}
}
