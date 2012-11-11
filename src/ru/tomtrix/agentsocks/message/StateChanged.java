package ru.tomtrix.agentsocks.message;

/** @author tom-trix */
public class StateChanged implements IMessage
{

	/** fsefe */
	private static final long	serialVersionUID	= -705717442418397054L;
	private final String		_variable;
	private final Object		_value;

	/** @param _variable
	 * @param _value */
	public StateChanged(String variable, Object value)
	{
		_variable = variable;
		_value = value;
	}

	/** @return the _variable */
	public String get_variable()
	{
		return _variable;
	}

	/** @return the _value */
	public Object get_value()
	{
		return _value;
	}
}
