package ru.tomtrix.agentsocks.mathmodel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tom-trix
 *
 */
public class State
{
	private final static String noSuchVariable = "Agent's state hasn't got variable \"%s\""; 
	private final Map<String, Object> _state = new ConcurrentHashMap<>();
	
	public void addStateItem(String key, StateItemAccess access, Object initialValue)
	{
		if (access != StateItemAccess.PUBLIC) throw new IllegalArgumentException("Not implemented");
		if (_state.containsKey(key)) throw new IllegalArgumentException(String.format("State has already got variable \"%s\"", key));
		_state.put(key, initialValue);
	}
	
	public Set<String> getStateItems()
	{
		return _state.keySet();
	}

	/**
	 * @param key
	 * @return value of the variable (<b>be careful, this retrieves only objects, not primitive types!</b>)
	 */
	public Object get(String key)
	{
		return _state.get(key);
	}


	/**
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	/** this method is used for the compability with the javassist library (cause it's not able to autopack primitive types)
	 * @param key
	 * @param value
	 */
	public void set(String key, boolean value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, byte value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, char value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, short value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, int value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, long value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, float value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
	
	public void set(String key, double value)
	{
		if (!_state.containsKey(key)) throw new IllegalArgumentException(String.format(noSuchVariable, key));
		_state.put(key, value);
	}
}
