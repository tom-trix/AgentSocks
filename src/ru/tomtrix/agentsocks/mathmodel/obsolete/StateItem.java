package ru.tomtrix.agentsocks.mathmodel.obsolete;

import javax.xml.bind.TypeConstraintException;

import ru.tomtrix.agentsocks.mathmodel.StateItemAccess;

@Deprecated
public class StateItem
{
	/** value of the variable */
	private Object			_value;
	/** type of the variable */
	private Class<?>		_class;
	/** level of the access */
	private StateItemAccess	_access;

	/** creates a new variable
	 * @param _class - type of the variable
	 * @param _access - level of the access
	 * @param initialValue - initial value of the variable */
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
