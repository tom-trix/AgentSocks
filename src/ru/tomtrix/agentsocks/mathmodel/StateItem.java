package ru.tomtrix.agentsocks.mathmodel;

import javax.xml.bind.TypeConstraintException;

public class StateItem
{
	/**
	 * 
	 */
	private Object			_value;
	/**
	 * 
	 */
	private Class<?>		_class;
	/**
	 * 
	 */
	private StateItemAccess	_access;

	/** @param _class
	 * @param _access */
	public StateItem(Class<?> clazz, StateItemAccess access, Object initialValue)
	{
		_class = clazz;
		_access = access;
		set_value(initialValue);
	}

	/** @return the _value */
	public Object get_value()
	{
		return _value;
	}

	/** @param _value the _value to set */
	public void set_value(Object _value)
	{
		if (!_class.isAssignableFrom(_value.getClass())) throw new TypeConstraintException(String.format("%s can't be cast to %s", _value.getClass(), _class));
		this._value = _value;
	}

	/** @return the _access */
	public StateItemAccess get_access()
	{
		return _access;
	}

	/** @param _access the _access to set */
	public void set_access(StateItemAccess _access)
	{
		this._access = _access;
	}
}
