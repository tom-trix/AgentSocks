package ru.tomtrix.agentsocks.mathmodel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** @author tom-trix */
public class State
{
	/** list of variables */
	private final Map<String, StateItem>	_list	= new ConcurrentHashMap<>();

	/** adds new variable to the state
	 * @param name - variable name
	 * @param clazz - variable type
	 * @param access - access level of the variable
	 * @param initialValue - the initial value of the variable
	 * @throws Exception - if something goes wrong */
	public void addStateItem(String name, Class<?> clazz, StateItemAccess access, Object initialValue) throws Exception
	{
		if (_list.containsKey(name)) throw new IllegalArgumentException("Such name already exists");
		_list.put(name, new StateItem(clazz, access, initialValue));
	}

	/** returns the value of the variable
	 * @param item
	 * @return */
	public Object get(String item)
	{
		return _list.get(item).get_value();
	}

	/** sets a new state for the variable
	 * @param item
	 * @param value */
	public void set(String item, Object value)
	{
		_list.get(item).set_value(value);
	}
}
